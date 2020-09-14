package hr.fer.zemris.java.webserver;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/** 
 * This class represents smart http server.
 * @author Daria Matković
 *
 */
public class SmartHttpServer {
	/** address **/
	private String address;
	/** domain name **/
	private String domainName;
	/** port **/
	private int port;
	/** worker threads **/
	private int workerThreads;
	/** session timeout **/
	private int sessionTimeout;
	/** mime types **/
	private Map<String,String> mimeTypes = new HashMap<String, String>();
	/** server thread **/
	private ServerThread serverThread;
	/** thread pool **/
	private ExecutorService threadPool;
	/** document root **/
	private Path documentRoot;
	/** workers map **/
	private Map<String,IWebWorker> workersMap = new HashMap<String, IWebWorker>();
	/** properties **/
	private Properties prop;
	/** daemon thread **/
	private DaemonThread cleanerThread = new DaemonThread();
	/** sid len **/
	private static final int SID_LEN = 20;
	/** help **/
	private String help;
	/** map where sid is key and SessionMapEntry is value **/
	private Map<String, SessionMapEntry> sessions =
			new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/** session random **/
	private Random sessionRandom = new Random();
	
	/**
	 * Creates and start smarthttpserver
	 * @param args config file
	 */
	public static void main(String[] args) {
		SmartHttpServer shs = new SmartHttpServer(args[0]);
		shs.start();
	}
	
	/**
	 * Daemon thread for saving memory
	 * @author Daria Matković
	 *
	 */
	public class DaemonThread extends Thread {
		/**
		 * Method for running thread
		 */
	    public void run() {
	    	
	    	try {
	    		while(true) {
		    		Thread.sleep(5*60*1000);
					
		    		for(String ses : sessions.keySet()) {
		    			if(System.currentTimeMillis() - sessions.get(ses).validUtil > sessionTimeout * 1000) {
		    				sessions.remove(ses);
		    			}
		    		}
		    	}

			} catch (Exception e) {
			}
	    }
	}
	
	/**
	 * Session map entry
	 * @author Daria Matković
	 *
	 */
	public static class SessionMapEntry {
		/** sid **/
		String sid;
		/** host **/
		String host;
		/** valid time **/
		long validUtil;
		/** map **/
		Map<String, String> map;
	}
	
	/**
	 * Constructor for smart http server that runs some initialization operations
	 * @param configFileName config file
	 */
	public SmartHttpServer(String configFileName) {
	// ... do stuff here ...
		prop = new Properties();
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
			
			FileInputStream fisFqcn = new FileInputStream(
					Paths.get(prop.getProperty("server.workers")).toString());
			
			Properties fqcnProp = new Properties();
			fqcnProp.load(fisFqcn);
			
			for(Object el : fqcnProp.keySet()) {
				Class<?> referenceToClass = 
						this.getClass().getClassLoader().loadClass(
								fqcnProp.getProperty(el.toString()));
				
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker)newObject;
				workersMap.put(el.toString(), iww);
			}
			
		} catch (Exception e) {
		}
		
	}
	
	/**
	 * Method executed at the beginning
	 */
	protected synchronized void start() {
		if(serverThread != null && serverThread.isAlive()) {
			return;
		}
		
		threadPool = Executors.newFixedThreadPool(workerThreads);
		serverThread = new ServerThread();
		serverThread.run();
		
		cleanerThread.setDaemon(true);
        cleanerThread.start();
	}
	
	/**
	 * method executed at the end
	 */
	protected synchronized void stop() {
	// ... signal server thread to stop running ...
	// ... shutdown threadpool ...
		threadPool.shutdown();
		serverThread.finish();
		cleanerThread.interrupt();
	}
	
	/**
	 * This class represents server thread
	 * @author Daria Matković
	 *
	 */
	protected class ServerThread extends Thread {
		/** flag to  check if operations are running **/
		boolean running = true;
		/** server socket **/
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
		
		/**
		 * Operations executed at the end
		 */
		public void finish() {
			running = false;
			try {
				serverSocket.close();
			} catch (IOException e) {
			}
		}
	}
	/**
	 * This class represents client worker
	 * @author Daria Matković
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/** request context **/
		private RequestContext context = null;
		/** socket **/
		private Socket csocket;
		/** input stream **/
		private PushbackInputStream istream;
		/** output stream **/
		private OutputStream ostream;
		/** version **/
		private String version;
		/** method **/
		private String method;
		/** host **/
		private String host;
		/** params **/
		private Map<String,String> params = new HashMap<String, String>();
		/** temp params **/
		private Map<String,String> tempParams = new HashMap<String, String>();
		/** perm params **/
		private Map<String,String> permPrams = new HashMap<String, String>();
		/** output cookies **/
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/** sid **/
		private String SID;
		
		/** client worker **/
		public ClientWorker(Socket csocket) {
			super();
		
			this.csocket = csocket;
			run();
		}
			
		@Override
		public void run() {
			
			if(csocket.isClosed()) {
				return;
			}
			
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
				
				for(int i = 1; i < request.size(); i++) {
					if(request.get(i).contains("Host:")) {
						this.host = checkName(request.get(i).substring(request.indexOf(":")+1)
								.trim());
						continue;
					}
					
					domainName = checkName(request.get(i).substring(request.indexOf(":")+1)
							.trim());
				}
				
				checkSession(request);
				
				if(requestedPath.startsWith("/ext/")) {
					IWebWorker iww = null;
					Class<?> referenceToClass;
					Object newObject = null;
					
					FileInputStream fis = new FileInputStream(
							Paths.get(prop.getProperty("server.workers")).toString());
					
					Properties pr = new Properties();
					pr.load(fis);
					
					try {
						
						String name = requestedPath.contains("?") ? 
								requestedPath.substring(5, requestedPath.indexOf("?")) :
									requestedPath.substring(5);
						
						referenceToClass = this.getClass()
								.getClassLoader().loadClass("hr.fer.zemris.java.webserver.workers." 
										+ name);
						
						newObject = referenceToClass.newInstance();
					} catch (Exception e) {
					}
					
					iww = (IWebWorker)newObject;
					
					if(iww != null) {
						context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
					
						try {
							iww.processRequest(context);
						} catch (Exception e) {
						}
						ostream.flush();
						return;
					}
				}
				
				if(requestedPath.contains("?")) {
					String path = requestedPath.substring(1, requestedPath.indexOf("?"));
					
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
					
					String pathCheck = requestedPath.split("\\?")[0];
					
					// checkSession(request);
					
					parseParameters(paramString);
					
					if(context == null) {
						context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
					}
					
					// for calc...
					if(workersMap.containsKey(pathCheck)) {
						try {
							workersMap.get(pathCheck).processRequest(context);
						} catch (Exception e) {
						}
						help = context.getPersistentParameter("bgcolor");

						ostream.flush();
						return;
					}
					
					requestedPath = documentRoot.toAbsolutePath().resolve(path).toString();
					
					if(!requestedPath.startsWith(documentRoot.toString())) {
						sendError(ostream, 403, "Response status forbidden");
						return;
					}
					
					if(checkSmscr(requestedPath)) {
						return;
					}
					
				} else {
					if(context == null) {
						context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
					}
					
					if(workersMap.containsKey(requestedPath)) {
						if(help != null) {
							context.setPersistentParameter("bgcolor", help);
						}
						
						try {
							workersMap.get(requestedPath).processRequest(context);
						} catch (Exception e) {
						}
						ostream.flush();
						
						System.out.println(help);
						//csocket.close();
						return;
					}
					requestedPath = documentRoot.toAbsolutePath().resolve(requestedPath.substring(1)).toString();
				}
				
				if(checkSmscr(requestedPath)) {
					return;
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
		
		/**
		 * Checks session
		 * @param request list of strings that represents request
		 */
		private synchronized void checkSession(List<String> request) {
			
			String sidCandidate = null;
			boolean set = false;
			
			for(String line : request) {
				if(!line.startsWith("Cookie")) {
					continue;
				}
				
				String cookiesString = line.replaceFirst("(Cookie:)*", "");
				
				// sid found
				if(cookiesString.contains("SID")) {
					String afterSid = cookiesString.split("SID")[1];
					sidCandidate = afterSid.split("\"")[1];
					
					if(SmartHttpServer.this.sessions.containsKey(sidCandidate)) {
						SessionMapEntry sme = SmartHttpServer.this.sessions.get(sidCandidate);
						
						// if stored host match with calculated host
						if(sme.host != null && sme.host.equals(this.host)) {
							// check if valid field is too old, remove and proceed just as
							// sid is not found
							if(System.currentTimeMillis() - sme.validUtil > sessionTimeout * 1000) {
								SmartHttpServer.this.sessions.remove(sidCandidate);
							} else {
								set = true;
								this.permPrams = sme.map;
								continue;
							}
							
						}
					} 
					//sidCandidate = createNewUniquesid();
					SessionMapEntry newSme = new SessionMapEntry();
					newSme.validUtil = System.currentTimeMillis() + sessionTimeout * 1000;
					newSme.host = this.host;
					newSme.map = new ConcurrentHashMap<String, String>();
					
					SmartHttpServer.this.sessions.put(sidCandidate, newSme);
					
					outputCookies.add(new RCCookie("SID", sidCandidate, null,
							host, "/"));
					this.permPrams = newSme.map;
					set = true;
				}
				return;
			}
			
			if(!set) {
				sidCandidate = createNewUniquesid();
				
				SessionMapEntry newSme = new SessionMapEntry();
				newSme.validUtil = System.currentTimeMillis() + sessionTimeout * 1000;
				newSme.sid = sidCandidate;
				newSme.map = new ConcurrentHashMap<String, String>();
				
				SmartHttpServer.this.sessions.put(sidCandidate, newSme);
				
				outputCookies.add(new RCCookie("SID", sidCandidate, null,
						host, "/"));
				this.permPrams = newSme.map;
			}
			
		}

		/**
		 * Creates new unique id
		 * @return unique id
		 */
		private String createNewUniquesid() {
			StringBuilder sb = new StringBuilder();
			
			for(int i = 0; i < SID_LEN; i++) {
				char help = (char)(sessionRandom.nextInt(26) + 'A');
				sb.append(help);
			}
			
			return sb.toString();
		}

		/**
		 * Checks smscr files 
		 * @param requestedPath requested path
		 * @return true if file has extension smscr, otherwise false
		 */
		private boolean checkSmscr(String requestedPath) {
			if("smscr".equals(getExtension(requestedPath))) {
				String documentBody = readFromDisk(requestedPath);
				DocumentNode dn = new SmartScriptParser(documentBody).getDocumentNode();
				
				RequestContext rc = new RequestContext(ostream, params,
						permPrams, outputCookies, tempParams, this);
				
				//rc.setMimeType("text/html");
				rc.setStatusText("OK");
				rc.setStatusCode(200);
				
				SmartScriptEngine sse = new SmartScriptEngine(dn, rc);
				sse.execute();
				try {
					ostream.flush();
					csocket.close();
				} catch (IOException e) {
				}
				
				//istream.close();
				return true;
			}
			return false;
		}
		
		/**
		 * Gets text
		 * @param cos output stream
		 * @param requestedFile requested path
		 * @param mime mime type 
		 * @throws IOException exception
		 */
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
			}
		}
		
		/** 
		 * Gets image 
		 * @param cos output stream
		 * @param img image
		 * @throws IOException exception
		 */
		private void getImage(OutputStream cos, BufferedImage img) throws IOException {
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
		
		/**
		 * Gets extension
		 * @param fileName file name
		 * @return extension
		 */
		private String getExtension(String fileName) {
			String fileExtension="";
			
			// If fileName do not contain "." or starts with "." then it is not a valid file
			if(fileName.contains(".") && fileName.lastIndexOf(".")!= 0) {
				fileExtension=fileName.substring(fileName.lastIndexOf(".")+1);
			}
			
			return fileExtension;
	    }

		/**
		 * Parse given string
		 * @param paramString string to parse
		 */
		private void parseParameters(String paramString) {
			String[] paramsArray = paramString.split("&");
			
			for(int i = 0; i < paramsArray.length; i++) {
				String[] elements = paramsArray[i].split("=");
				
				this.params.put(elements[0], elements[1]);
			}
		}

		/**
		 * Checks name
		 * @param name name
		 * @return part of name
		 */
		private String checkName(String name) {
			if(name.contains(":") && isNumeric(name.substring(name.indexOf(":")+1))) {
				return name.substring(0, name.indexOf(":"));
			}
			return name;
		}
		
		/**
		 * Extract headers splits request header string in list of strings
		 * @param requestHeader
		 * @return
		 */
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
		
		/**
		 * Check if given value is number
		 * @param str value to check
		 * @return true if given value is number, otherwise false
		 */
		private boolean isNumeric(String str) { 
			try {  
			  Double.parseDouble(str);  
			  return true;
		  	} catch(NumberFormatException e){  
			  return false;  
		  	}  
		}
		
		/**
		 * Sends error
		 * @param cos output stream
		 * @param statusCode status code
		 * @param statusText status text
		 * @throws IOException exception
		 */
		private void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {

				cos.write(
					("HTTP/1.1 " + statusCode + " " + statusText + "\r\n"+
					"Server: simple java server\r\n"+
					"Content-Type: text/plain;charset=UTF-8\r\n"+
					"Content-Length: 0\r\n"+
					"Connection: close\r\n"+
					"\r\n").getBytes(StandardCharsets.US_ASCII)
				);
				cos.flush();
		}
		
		/**
		 * Read request
		 * @param is input stream
		 * @return byte array 
		 * @throws IOException exception
		 */
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
		
		/**
		 * Internal dispatching method called by dispatch request
		 * @param requestedPath requested path
		 * @param directCall direct call
		 * @throws Exception exception
		 */
		private void internalDispatchRequest(String requestedPath, boolean directCall)
				throws Exception {
			String extension;
			
			if(("private".equals(requestedPath) || requestedPath.startsWith("private/"))
					&& directCall) {
				sendError(ostream, 404, "File not ok");
				return;
			}
			
			if(!Paths.get(requestedPath).isAbsolute()) {
				requestedPath = documentRoot.toAbsolutePath().resolve(requestedPath).toString();
			}
			
			if(checkSmscr(requestedPath)) {
				return;
			}
			
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
			
			if(context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
			}
			
			if(!context.headerGenerated) {
				//context.setMimeType(mimeType);
				context.setStatusCode(200);
				context.setStatusText("OK");
			}
			
			
			if(!"png".equals(extension)) {
				//rc.write(Files.readString(Paths.get(requestedPath)));
				getText(ostream, Paths.get(requestedPath), mimeType);
			} else {
				getImage(ostream, ImageIO.read(new File(requestedPath)));
			}
			ostream.flush();
		}
	}
	
	/**
	 * Reads from disk
	 * @param filePath file path
	 * @return String with file contents
	 */
	private static String readFromDisk(String filePath) {
		try {
			return new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			System.out.println("Can't open file");
			return null;
		}
	}
}
