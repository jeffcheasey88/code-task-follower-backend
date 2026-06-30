package be.jeffcheasey88.codetaskfollower.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import be.jeffcheasey88.codetaskfollower.validator.MinValidator.Min;
import be.jeffcheasey88.codetaskfollower.validator.MinValidator.MinContext;

public class MinValidator extends Validator<Min, MinContext>{
	
	@Retention(RUNTIME)
	@Target(FIELD)
	public @interface Min{
		
		int value() default 1;

	    ValidatorMessage message() default ValidatorMessage.TOO_SHORT;
	}

	class MinContext implements ValidatorContext{
		
		private Min min;
		
		MinContext(Min min){
			this.min = min;
		}
		
		@Override
		public ValidatorMessage validate(Object object){
			int min = this.min.value();
			ValidatorMessage message = this.min.message();
			
			if(object == null) return message;
			if(object instanceof String string && string.length() < min) return message;
			if(object instanceof Number number && number.longValue() < min) return message;
			return null;
		}
	}

	@Override
	public MinContext context(Min annotation){
		return new MinContext(annotation);
	}
}
