package be.jeffcheasey88.codetaskfollower.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import be.jeffcheasey88.codetaskfollower.exception.HttpError;
import be.jeffcheasey88.codetaskfollower.exception.InvalidDataError;
import be.jeffcheasey88.codetaskfollower.validator.MaxValidator.Max;
import be.jeffcheasey88.codetaskfollower.validator.MinValidator.Min;
import be.jeffcheasey88.codetaskfollower.validator.RegexValidator.Regex;

public abstract class Validator<A extends Annotation, C extends ValidatorContext> {
	
	public abstract C context(A annotation);
	
	private static Map<Class<?>, Validator<Annotation, ValidatorContext>> validatorFunctions;
	private static Map<Annotation, ValidatorContext> validatorContexts;

    static {
    	validatorContexts = new HashMap<>();
        validatorFunctions = new HashMap<>();
        register(Min.class, new MinValidator());
        register(Max.class, new MaxValidator());
        register(Regex.class, new RegexValidator());
    }
    
    private static <N extends Annotation> void register(Class<? extends N> annotation, Validator<N, ?> validator){
    	validatorFunctions.put(annotation, (Validator<Annotation, ValidatorContext>) validator);
    }
	
	public static <T> T check(Class<?> c, String fieldName, T value) throws InvalidDataError, HttpError {
		Field field;
		
		try {
			field = c.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			throw new HttpError(500, "No field named " + fieldName + " found in class " + c.getName(), e);
		}
		
		List<ValidatorMessage> validatorMessages = new ArrayList<>();
		
		field.setAccessible(true);
        for(Annotation annotation : field.getAnnotations()){
        	ValidatorContext context = validatorContexts.get(annotation);
        	if(context == null){
        		Validator<Annotation, ValidatorContext> validator = validatorFunctions.get(annotation.annotationType());
        		if(validator == null) continue;
        		validatorContexts.put(annotation, context = validator.context(annotation));
        	}
        	
        	ValidatorMessage message = context.validate(value);
        	if(message != null) validatorMessages.add(message);
        }
        
        if (!validatorMessages.isEmpty()) {
        	throw new InvalidDataError(validatorMessages);
        }
        
        return value;
	}
	
}
