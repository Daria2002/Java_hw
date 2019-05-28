package hr.fer.zemris.java.webserver;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartHttpServer {

	private String address;
	private String domainName;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String,String> mimeTypes = new HashMap<String, String>();
	private ServerThread serverThread;
	private ExecutorService threadPool;
	private Path documentRoot;
	
	// /home/daria/eclipse-workspace/my-hw/hw12-0036480046/config/server.properties
	public static void main(String[] args) {
		SmartHttpServer shs = new SmartHttpServer(args[0]);
		shs.start();
	}
	
	public SmartHttpServer(String configFileName) {
	// ... do stuff here ...
		Properties prop = new Properties();
		FileInputStream fis;
		
		try {
			fis = new FileInputStream(configFileName);
			prop.load(fis);
			address = prop.getProperty("server.address");
			domainName = prop.getProperty("server.domainName");
			port = Integer.valueOf(prop.getProperty("server.port"));
			workerThreads = Integer.valueOf(prop.getProperty("server.workerThreads"));
			sessionTimeout = Integer.valueOf(prop.getProperty("session.timeout"));
			documentRoot = Paths.get(prop.getProperty("server.documentRoot"));
			
			FileInputStream mime = new FileInputStream(
					Paths.get(prop.getProperty("server.mimeConfig")).toString());
			Properties mimeProp = new Properties();
			mimeProp.load(mime);
			for(Object el : mimeProp.keySet()) {
				mimeTypes.put(el.toString(), mimeProp.getProperty(el.toString()));
			}
			
		} catch (Exception e) {
		}
		
	}
	
	protected synchronized void start() {
		if(serverThread != null && serverThread.isAlive()) {
			return;
		}
		
		threadPool = Executors.newFixedThreadPool(workerThreads);
		serverThread = new ServerThread();
		serverThread.run();
		// TODO:session cleaner
	}
	
	protected synchronized void stop() {
	// ... signal server thread to stop running ...
	// ... shutdown threadpool ...
		threadPool.shutdown();
		serverThread.finish();
	}
	
	protected class ServerThread extends Thread {
		boolean running = true;
		ServerSocket serverSocket;
		
		@Override
		public void run() {
			// given in pesudo-code:
			// open serverSocket on specified port
			// while(true) {
			// Socket client = serverSocket.accept();
			// ClientWorker cw = new ClientWorker(client);
			// submit cw to threadpool for execution
			// }
			
			try {
				serverSocket = new ServerSocket();
				SocketAddress socketAddress = new InetSocketAddress(
						InetAddress.getByName(SmartHttpServer.this.address), 
						SmartHttpServer.this.port);
				serverSocket.bind(socketAddress);
				
				serverSocket.setSoTimeout(SmartHttpServer.this.sessionTimeout * 1000);

				
				
				while(running) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
					
				      
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				throw new IllegalArgumentException("Server socket can't open given port");
			}
		}
		
		public void finish() {
			running = false;
			try {
				serverSocket.close();
			} catch (IOException e) {
			}
		}
	}
	
	private class ClientWorker implements Runnable, IDispatcher {
		private Socket csocket;
		private PushbackInputStream istream;
		private OutputStream ostream;
		private String version;
		private String method;
		private String host;
		private Map<String,String> params = new HashMap<String, String>();
		private Map<String,String> tempParams = new HashMap<String, String>();
		private Map<String,String> permPrams = new HashMap<String, String>();
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		private String SID;
		
		public ClientWorker(Socket csocket) {
			super();
		
			this.csocket = csocket;
			run();
		}
			
		@Override
		public void run() {
			try {
				// obtain input stream from socket
				istream = new PushbackInputStream(csocket.getInputStream());
				// obtain output stream from socket
				ostream = csocket.getOutputStream();
				// Then read complete request header from your client in separate method...
				byte[] b = readRequest(istream);
				if(b == null) {
					return;
				}
				String requested = new String(b, StandardCharsets.US_ASCII);
				
				
				List<String> request = extractHeaders(requested);
				
				// If header is invalid (less then a line at least) return response status 400
				if(request.size() < 1) {
					sendError(ostream, 400, "Header is not ok");
					return;
				}
				
				String firstLine = request.get(0);
				
				String[] firstLineArray = firstLine.split(" ");
				
				this.method = firstLineArray[0];
				String requestedPath = firstLineArray[1];
				version = firstLineArray[2];
				
				if(requestedPath.contains("?")) {
					String path = requestedPath.substring(0, requestedPath.indexOf("?"));
					
					String paramString = requestedPath.substring(requestedPath.indexOf("?") + 1);
					
					if(!"GET".equals(method) || (!"HTTP/1.0".equals(version) &&
							!"HTTP/1.1".equals(version))) {
						sendError(ostream, 400, "Not ok method or version");
						return;
					}
					
					for(int i = 1; i < request.size(); i++) {
						if(request.get(i).contains("Host:")) {
							this.host = checkName(request.get(i).substring(request.indexOf(":")+1)
									.trim());
							continue;
						}
						
						domainName = checkName(request.get(i).substring(request.indexOf(":")+1)
								.trim());
					}
					requestedPath = documentRoot.toAbsolutePath().resolve(path).toString();
					
					if(!requestedPath.startsWith(documentRoot.toString())) {
						sendError(ostream, 403, "Response status forbidden");
						return;
					}
					
					parseParameters(paramString);
					
					if(getExtension(path) == "smscr") {
						String documentBody = readFromDisk(path);

						DocumentNode dn = new SmartScriptParser(documentBody).getDocumentNode();
						RequestContext rc = new RequestContext(ostream, params,
								permPrams, outputCookies, tempParams, ClientWorker.this);
						
						SmartScriptEngine sse = new SmartScriptEngine(dn, rc);
						sse.execute();
					}
					
				} else {
					requestedPath = documentRoot.toAbsolutePath().resolve(requestedPath.substring(1)).toString();
				}
				
				try {
					internalDispatchRequest(requestedPath, true);
				} catch (Exception e) {
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				throw new IllegalArgumentException("Can't get output stream");
			}
		}
		
		private void getText(OutputStream cos, Path requestedFile, String mime) throws IOException {
			long len = Files.size(requestedFile);
			try(InputStream fis = new BufferedInputStream(Files.newInputStream(requestedFile))) {
				
				cos.write(
						("HTTP/1.1 200 OK\r\n"+
						"Server: simple java server\r\n"+
						"Content-Type: "+ mime+ "\r\n"+
						"Content-Length: "+ len+"\r\n"+
						"Connection: close\r\n"+
						"\r\n").getBytes(StandardCharsets.US_ASCII)
					);
					cos.flush();
				
					byte[] buf = new byte[1024];
					while(true) {
						int r = fis.read(buf);
						if(r<1) break;
						cos.write(buf, 0, r);
					}
					cos.flush();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		private void vratiSliku(OutputStream cos, BufferedImage img) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", bos);
			byte[] podaci = bos.toByteArray();
			
			cos.write(
					("HTTP/1.1 200 OK\r\n"+
					"Server: simple java server\r\n"+
					"Content-Type: image/png\r\n"+
					"Content-Length: "+ podaci.length+"\r\n"+
					"Connection: close\r\n"+
					"\r\n").getBytes(StandardCharsets.US_ASCII)
			);
			
			cos.write(podaci);
		}
		
		private String getExtension(String fileName) {
			String fileExtension="";
			
			// If fileName do not contain "." or starts with "." then it is not a valid file
			if(fileName.contains(".") && fileName.lastIndexOf(".")!= 0) {
				fileExtension=fileName.substring(fileName.lastIndexOf(".")+1);
			}
			
			return fileExtension;
	    }

		private void parseParameters(String paramString) {
			String[] paramsArray = paramString.split("&");
			
			for(int i = 0; i < paramsArray.length; i++) {
				String[] elements = paramsArray[i].split("=");
				
				params.put(elements[0], elements[1]);
			}
		}

		private String checkName(String name) {
			if(name.contains(":") && isNumeric(name.substring(name.indexOf(":")+1))) {
				return name.substring(0, name.indexOf(":"));
			}
			return name;
		}
		
		// Zaglavlje predstavljeno kao jedan string splita po enterima
		// pazeći na višeretčane atribute...
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for(String s : requestHeader.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				// tab ili space
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}
		
		private boolean isNumeric(String str) { 
			try {  
			  Double.parseDouble(str);  
			  return true;
		  	} catch(NumberFormatException e){  
			  return false;  
		  	}  
		}
		
		private void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {

				/*cos.write(
					("HTTP/1.1 " + statusCode + " " + statusText + "\r\n"+
					"Server: simple java server\r\n"+
					"Content-Type: text/plain;charset=UTF-8\r\n"+
					"Content-Length: 0\r\n"+
					"Connection: close\r\n"+
					"\r\n").getBytes(StandardCharsets.US_ASCII)
				);*/
				cos.flush();
		}

		// Jednostavan automat koji čita zaglavlje HTTP zahtjeva...
		private byte[] readRequest(InputStream is) throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
	l:		while(true) {
				int b = is.read();
				if(b==-1) return null;
				if(b!=13) {
					bos.write(b);
				}
				switch(state) {
				case 0: 
					if(b==13) { state=1; } else if(b==10) state=4;
					break;
				case 1: 
					if(b==10) { state=2; } else state=0;
					break;
				case 2: 
					if(b==13) { state=3; } else state=0;
					break;
				case 3: 
					if(b==10) { break l; } else state=0;
					break;
				case 4: 
					if(b==10) { break l; } else state=0;
					break;
				}
			}
			return bos.toByteArray();
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		private void internalDispatchRequest(String requestedPath, boolean b)
				throws Exception {
			String extension;
			
			if(Files.exists(Paths.get(requestedPath)) &&
					!Files.isDirectory(Paths.get(requestedPath)) &&
					Files.isReadable(Paths.get(requestedPath))) {
				extension = getExtension(requestedPath);
			} else {
				sendError(ostream, 404, "File not ok");
				return;
			}
			
			String mimeType = SmartHttpServer.this.mimeTypes.get(extension);
			
			if(mimeType == null) {
				mimeType = "application/octet-stream";
			}
			
			RequestContext rc = new RequestContext(ostream, params, permPrams, outputCookies);
			rc.setMimeType(mimeType);
			rc.setStatusCode(200);
			rc.setStatusText("OK");
			
			if(!"png".equals(extension)) {
				//rc.write(Files.readString(Paths.get(requestedPath)));
				getText(ostream, Paths.get(requestedPath), mimeType);
			} else {
				vratiSliku(ostream, ImageIO.read(new File(requestedPath)));
			}
			
			ostream.flush();
		}
	}
	
	private static String readFromDisk(String fileName) {
		try {
			String filePath = System.getProperty("user.dir") + 
					"/src/main/java/hr/fer/zemris/java/custom/scripting/demo/"+fileName;
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			System.out.println("Can't open file");
			return null;
		}
	}
}
