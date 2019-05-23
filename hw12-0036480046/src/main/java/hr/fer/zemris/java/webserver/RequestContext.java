package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class RequestContext {
	
	private OutputStream outputStream;
	private Charset charset;
	
	public String encoding = "UTF-8";
	public int statusCode = 200;
	public String statusText = "OK";
	public String mimeType = "text/html";
	public Long contentLength = null;
	
	private Map<String, String> parameters;
	private Map<String, String> temporaryParameters;
	private Map<String, String> persistentParameters;
	private List<RCCookie> outputCookies;
	boolean headerGenerated = false;
	
	public RequestContext(OutputStream outputStream, Map<String,String> parameters,
			Map<String,String> persistentParameters, List<RCCookie> outputCookies) {
		
		Objects.requireNonNull(outputStream);
		
		this.outputStream = outputStream;
		
		this.parameters = parameters == null ? new HashMap<String, String>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<String, String>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<RequestContext.RCCookie>() : outputCookies;
	}
	
	/**
	 * method that retrieves value from parameters map (or null if no association exists)
	 * @param name key
	 * @return value
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * method that retrieves names of all parameters in parameters map (note, this set must be read-only)
	 * @return names of all parameters in parameters map
	 */
	public Set<String> getParameterNames() {
		Set<String> nameSet = new HashSet<String>();
		
		for(String name : parameters.keySet()) {
			nameSet.add(name);
		}
		
		return nameSet;
	}
	
	/**
	 * method that retrieves value from persistentParameters map (or null if no association exists)
	 * @param name key
	 * @return value
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * method that retrieves names of all parameters in persistent parameters map (note, this set must be readonly)
	 * @return
	 */
	public Set<String> getPersistentParameterNames() {
		Set<String> nameSet = new HashSet<String>();
		
		for(String name : persistentParameters.keySet()) {
			nameSet.add(name);
		}
		
		return nameSet;
	}
	
	/**
	 * method that stores a value to persistentParameters map
	 * @param name key
	 * @param value value
	 */
	public void setPersistentParameter(String name, String value) {
		this.persistentParameters.put(name, value);
	}
	
	/**
	 * method that removes a value from persistentParameters map
	 * @param name
	 */
	public void removePersistentParameter(String name) {
		this.persistentParameters.remove(name);
	}
	
	/**
	 * method that retrieves value from temporaryParameters map (or null if no association exists)
	 * @param name key
	 * @return value
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * method that retrieves names of all parameters in temporary parameters map (note, this set must be read-only)
	 * @return names set
	 */
	public Set<String> getTemporaryParameterNames() {
		Set<String> nameSet = new HashSet<String>();
		
		for(String name : temporaryParameters.keySet()) {
			nameSet.add(name);
		}
		
		return nameSet;
	}
	
	/**
	 * method that retrieves an identifier which is unique for current user session
	 * @return identifier
	 */
	public String getSessionID() {
		return "";
	}
	
	public RequestContext write(byte[] data) throws IOException {
		if(!headerGenerated) {
			createHeader();
		}

		charset = Charset.forName(encoding);
		outputStream.write(data);
		return this;
	}
	
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if(!headerGenerated) {
			createHeader();
		}

		charset = Charset.forName(encoding);
		outputStream.write(data, offset, len);
		return this;
	}
	
	public RequestContext write(String text) throws IOException {
		if(!headerGenerated) {
			createHeader();
		}
		
		charset = Charset.forName(encoding);
		byte[] b = text.getBytes(charset);
		outputStream.write(b);
		return this;
	}
	
	private void createHeader() {
		
		/*
		 *So how does the header looks like? Properties used for header construction are encoding, statusCode,
statusText, mimeType, outputCookies, contentLength .
Header is obtained by serializing a several lines of text into bytes using codepage ISO_8859_1 (see
StandardCharsets ). Lines are separated by "\r\n". First line must be of form:
“ HTTP/1.1 ” statusCode statusMessage
Second line must be of form:
“ Content-Type: “ mimeType
If mime type starts with “ text/ ” (for example, “ text/html ” or “ text/plain ”), you should append on
mime-type “ ; charset= ” encoding.
		
If property contentLength is not null, add next line to headers:
“ Content-Length: “ contentLength
If contentLength is null, previous line must not be included; this way, the consumer of the sent data will*/
/*
read all the data we send through the output stream, until the stream is closed.If list of outputCookies is not empty, for each cookie you should emit a single line of form:
' Set-Cookie: ' name ' =” ' value ' ”; Domain= ' domain ' ; Path= ' path ' ; Max-Age= ' maxAge
domain, path and maxAge are included only if they are not null in given cookie object. For example, for a
cookie with only name set to 'korisnik' and value set to 'perica' you would emit:
Set-Cookie: korisnik="perica"
If cookie also included maxAge set to 3600 you would instead emit a line:
Set-Cookie: korisnik="perica"; Max-Age=3600
Finally, another empty line should be emitted to signal the end of headers. 
		 * 
		 */
		
		StringBuilder header = new StringBuilder();
		
		header.append(" HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		header.append(" Content-Type: " + mimeType);
		
		if(appendCharset()) {
			header.append(" ; charset= " + encoding);
		}
		
		header.append("\r\n");
		
		if(contentLength != null) {
			header.append(" Content-Length: " + contentLength + "\r\n");
		}
		
		if(!outputCookies.isEmpty()) {
			for(RCCookie element : outputCookies) {
				
				boolean separate = false;
				
				header.append("Set-Cookie: ");
				
				if(element.name != null) {
					header.append(element.name + "=" + "\"" + element.value + "\"");
					separate = true;
				}
				
				if(element.domain != null) {
					header.append((separate ? ";" : "") + "Domain=" + element.domain);
				}
				
				if(element.path != null) {
					header.append((separate ? ";" : "") + "Path=" + element.path);
				}
				
				if(element.maxAge != null) {
					header.append((separate ? ";" : "") + "Max-Age=" + element.maxAge);
				}
				
				header.append("\r\n");
			}
		}
		
		byte[] b = header.toString().getBytes(Charset.forName("ISO_8859_1"));
		try {
			this.outputStream.write(b);
		} catch (IOException e) {
		}
	}

	private boolean appendCharset() {
		if("text".equals(mimeType.trim().split("/")[0])) {
			return true;
		}
		
		return false;
	}

	/**
	 * method that stores a value to temporaryParameters map
	 * @param name key
	 * @param value value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
		
	/**	
	 * method that removes a value from temporaryParameters map
	 * @param name key
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}
	
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setEncoding(String encoding) {
		if(headerGenerated) {
			throw new RuntimeException("Header is generated, can't call setEncoding");
		}
		this.encoding = encoding;
	}

	public void setStatusCode(int statusCode) {
		if(headerGenerated) {
			throw new RuntimeException("Header is generated, can't call setStatusCode");
		}
		this.statusCode = statusCode;
	}

	public void setStatusText(String statusText) {
		if(headerGenerated) {
			throw new RuntimeException("Header is generated, can't call setStatusText");
		}
		this.statusText = statusText;
	}

	public void setMimeType(String mimeType) {
		if(headerGenerated) {
			throw new RuntimeException("Header is generated, can't call setMimeTType");
		}
		this.mimeType = mimeType;
	}

	public void setContentLength(Long contentLength) {
		if(headerGenerated) {
			throw new RuntimeException("Header is generated, can't call setContentLength");
		}
		this.contentLength = contentLength;
	}

	public void addRCCookie(RCCookie rcCookie) {
		if(headerGenerated) {
			throw new RuntimeException("Header is generated, can't call addRCCookie");
		}
		outputCookies.add(rcCookie);
	}

	public static class RCCookie {
		
		private String name;
		private String value;
		private String domain;
		private String path;
		private Integer maxAge;
		
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
		}
		public String getName() {
			return name;
		}
		public String getValue() {
			return value;
		}
		public String getDomain() {
			return domain;
		}
		public String getPath() {
			return path;
		}
		public Integer getMaxAge() {
			return maxAge;
		}
	}
}
