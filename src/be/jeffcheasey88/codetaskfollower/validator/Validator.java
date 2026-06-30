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

public class Validator {
	private static Map<Class<?>, BiFunction<Object, Annotation, ValidatorMessage>> validatorFunctions;

    static {
        validatorFunctions = new HashMap<>();
        validatorFunctions.put(Min.class, (object, annotation) -> validateFieldMin(object, (Min) annotation));
        validatorFunctions.put(Max.class, (object, annotation) -> validateFieldMax(object, (Max) annotation));
        validatorFunctions.put(Regex.class, (object, annotation) -> validateFieldRegex(object, (Regex) annotation));
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
		Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
        	final BiFunction<Object, Annotation, ValidatorMessage> validatorFunction = validatorFunctions
                    .get(annotation.annotationType());
        	
            if (validatorFunction != null) {
                final ValidatorMessage message = validatorFunction.apply(value, annotation);
                if (message != null) {
                    validatorMessages.add(message);
                }
            }
        }
        
        if (!validatorMessages.isEmpty()) {
        	throw new InvalidDataError(validatorMessages);
        }
        
        return value;
	}
	
	private static ValidatorMessage validateFieldMin(Object object, Min min) {
        if (object == null || (object instanceof String string && string.length() < min.value())
                || (object instanceof Number number && number.longValue() < min.value())) {
            return min.message();
        }
        return null;
    }

    private static ValidatorMessage validateFieldMax(Object object, Max max) {
        if (object == null) {
            return null;
        }

        if ((object instanceof String string && string.length() > max.value())
                || (object instanceof Number number && number.longValue() > max.value())) {
            return max.message();
        }
        return null;
    }

    private static ValidatorMessage validateFieldRegex(Object object, Regex regex) {
        if (object == null) {
            return null;
        }

        if (!Pattern.compile(regex.value()).matcher(object.toString()).matches()) {
            return regex.message();
        }
        return null;
    }
}
