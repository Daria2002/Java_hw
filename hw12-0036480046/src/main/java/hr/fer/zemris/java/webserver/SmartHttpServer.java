package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
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
				istream = (PushbackInputStream) csocket.getInputStream();
				ostream = csocket.getOutputStream();
				List<String> request = readRequest();
				
				if(request.size() < 1) {
					
				}
				
			} catch (IOException e) {
				throw new IllegalArgumentException("Can't get output stream");
			}
			
			List<String> request = readRequest();
		}

		private List<String> readRequest() {
			// TODO Auto-generated method stub
			return null;
		}
	}	
}