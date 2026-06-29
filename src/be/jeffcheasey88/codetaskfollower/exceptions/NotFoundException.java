package be.jeffcheasey88.codetaskfollower.exceptions;

public class NotFoundException extends ClientErrorException {
	@Override
	public short getHttpResponseStatusCode() {
		return 404;
	}
}
