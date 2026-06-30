package be.jeffcheasey88.codetaskfollower.exception;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.validator.ValidatorMessage;

public class InvalidDataError extends HttpError {
	private List<ValidatorMessage> messages;
	
	public InvalidDataError(List<ValidatorMessage> messages) {
		super(400);
		
		this.messages = messages;
	}
}
