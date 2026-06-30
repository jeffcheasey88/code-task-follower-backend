package be.jeffcheasey88.codetaskfollower.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

import be.jeffcheasey88.codetaskfollower.validator.RegexValidator.Regex;
import be.jeffcheasey88.codetaskfollower.validator.RegexValidator.RegexContext;

public class RegexValidator extends Validator<Regex, RegexContext>{
	
	@Retention(RUNTIME)
	@Target(FIELD)
	public @interface Regex {
		String value();

	    ValidatorMessage message() default ValidatorMessage.INVALID_FORMAT;
	}

	class RegexContext implements ValidatorContext{

		private Pattern pattern;
		private ValidatorMessage message;
		
		RegexContext(Regex annotation){
			this.pattern = Pattern.compile(annotation.value());
			this.message = annotation.message();
		}
		
		@Override
		public ValidatorMessage validate(Object object){
			if(object == null || pattern.matcher(object.toString()).matches()) return null;
	        return message;
		}
		
	}

	@Override
	public RegexContext context(Regex annotation){
		return new RegexContext(annotation);
	}
}
