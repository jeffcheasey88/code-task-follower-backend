package be.jeffcheasey88.codetaskfollower.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import be.jeffcheasey88.codetaskfollower.validator.MaxValidator.Max;
import be.jeffcheasey88.codetaskfollower.validator.MaxValidator.MaxContext;

public class MaxValidator extends Validator<Max, MaxContext>{
	
	@Retention(RUNTIME)
	@Target(FIELD)
	public @interface Max{
		
		int value() default 255;

	    ValidatorMessage message() default ValidatorMessage.TOO_LONG;
	}

	class MaxContext implements ValidatorContext{
		
		private Max max;
		
		MaxContext(Max max){
			this.max = max;
		}
		
		@Override
		public ValidatorMessage validate(Object object){
			int min = max.value();
			ValidatorMessage message = max.message();
			
			if(object == null) return message;
			if(object instanceof String string && string.length() > min) return message;
			if(object instanceof Number number && number.longValue() > min) return message;
			return null;
		}
	}

	@Override
	public MaxContext context(Max annotation){
		return new MaxContext(annotation);
	}
}
