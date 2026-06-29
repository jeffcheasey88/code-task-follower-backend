package be.jeffcheasey88.codetaskfollower.exceptions;

public abstract class ClientErrorException extends Exception {
	public abstract short getHttpResponseStatusCode();
}
