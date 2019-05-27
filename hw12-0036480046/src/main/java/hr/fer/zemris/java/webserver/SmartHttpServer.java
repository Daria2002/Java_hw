package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

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
	
	public static void main(String[] args) {
		
	}
	
	public SmartHttpServer(String configFileName) {
	// ... do stuff here ...
	}
	
	protected synchronized void start() {
	// ... start server thread if not already running ...
	// ... init threadpool by Executors.newFixedThreadPool(...); ...
	}
	
	protected synchronized void stop() {
	// ... signal server thread to stop running ...
	// ... shutdown threadpool ...
	}
	
	protected class ServerThread extends Thread {
		@Override
		public void run() {
			// given in pesudo-code:
			// open serverSocket on specified port
			// while(true) {
			// Socket client = serverSocket.accept();
			// ClientWorker cw = new ClientWorker(client);
			// submit cw to threadpool for execution
			// }
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket(SmartHttpServer.this.port);
				
				while(true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.execute(cw);
				}
				
			} catch (IOException e) {
				throw new IllegalArgumentException("Server socket can't open given port");
			}
		}
	}
	
	private class ClientWorker implements Runnable {
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
		}
			
		@Override
		public void run() {
			try {
				// obtain input stream from socket
				istream = (PushbackInputStream) csocket.getInputStream();
				// obtain output stream from socket
				ostream = csocket.getOutputStream();
				// Then read complete request header from your client in separate method...
				List<String> request = readRequest();
				
				// If header is invalid (less then a line at least) return response status 400
				if(request.size() < 1) {
					sendError(ostream, 400, "Invalid header");
				}
				
				String firstLine = request.get(0);
				extract(firstLine);
				
				
			} catch (IOException e) {
				throw new IllegalArgumentException("Can't get output stream");
			}
			
			List<String> request = readRequest();
		}

		
		
		
		
		private void extract(String firstLine) throws IOException {
			this.method = firstLine.trim().substring(0, 3);
			String requestedPath = firstLine.substring(firstLine.indexOf("/"),
					firstLine.substring(firstLine.indexOf("/")).indexOf(" "));
			version = firstLine.substring(firstLine.substring(
					firstLine.indexOf("/")).indexOf(" ") + 1).trim();
			String path = requestedPath.substring(0, requestedPath.indexOf("?"));
			String paramString = requestedPath.substring(requestedPath.indexOf("?") + 1);
			
			if(!"GET".equals(method) || !"HTTP/1.0".equals(version) ||
					!"HTTP/1.1".equals(version)) {
				sendError(this.ostream, 400, "Not ok method or version");
			}
			
			
			
		}

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

		private List<String> readRequest() {
			List<String> request;
			
			
			String line;
			while ((line=istream.read()) != null) {
			    myDict.add(line);
			}
		}
	}	
}
