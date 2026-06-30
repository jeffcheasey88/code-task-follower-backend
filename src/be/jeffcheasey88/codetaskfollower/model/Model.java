package be.jeffcheasey88.codetaskfollower.model;

import be.jeffcheasey88.codetaskfollower.validator.Validator;

public abstract class Model {
	protected <T> T check(String fieldName, T fieldValue) {
		return Validator.check(getClass(), fieldName, fieldValue);
	}
}
