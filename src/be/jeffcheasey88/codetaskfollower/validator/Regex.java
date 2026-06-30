package be.jeffcheasey88.codetaskfollower.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Regex {
	String value();

    ValidatorMessage message() default ValidatorMessage.INVALID_FORMAT;
}
