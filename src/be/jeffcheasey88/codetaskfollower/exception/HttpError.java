package be.jeffcheasey88.codetaskfollower.exception;

public class HttpError extends RuntimeException {
	private int httpResponseStatusCode;
	
	public HttpError(int httpResponseStatusCode) {
		this.httpResponseStatusCode = httpResponseStatusCode;
	}
	
	public HttpError(int httpResponseStatusCode, String message, Throwable cause) {
		super(httpResponseStatusCode + " " + message, cause);
		this.httpResponseStatusCode = httpResponseStatusCode;
	}
	
	public HttpError(int httpResponseStatusCode, String message) {
		super(httpResponseStatusCode + " " + message);
		this.httpResponseStatusCode = httpResponseStatusCode;
	}
	
	public HttpError(int httpResponseStatusCode, Throwable cause) {
		super(cause);
		this.httpResponseStatusCode = httpResponseStatusCode;
	}
	
	public boolean isClientError() {
		return httpResponseStatusCode >= 400 && httpResponseStatusCode < 500;
	}
	
	public int getHttpResponseStatusCode() {
		return httpResponseStatusCode;
	}
}
