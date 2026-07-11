package be.jeffcheasey88.codetaskfollower.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Configuration{
	
	@Alias(value="no-register", process="noBoolean")
	@Alias(value="register", process="booleanValue")
	private boolean allowRegister = true;
	
	@Alias(value="no-color", process="noBoolean")
	@Alias(value="color", process="booleanValue")
	private boolean allowColor = true;
	
	

	public Configuration(){}
	
	public void setValue(String key, String value) throws Exception{
		Field field = getClass().getDeclaredField(key);
		field.setAccessible(true);
		Class<?> type = field.getType();
		if(type.isPrimitive()){
			if(type.equals(Boolean.TYPE)){
				field.set(this, Boolean.parseBoolean(value));
			}
		}
	}
	
	public void processAlias(String alias) throws Exception{
		for(Field field : getClass().getDeclaredFields()){
			field.setAccessible(true);
			Aliases aliases = field.getAnnotation(Aliases.class);
			if(aliases == null) continue;
			for(Alias aliasAnnotation : aliases.value()){
				if(aliasAnnotation.value().equals(alias)){
					Method method = getClass().getDeclaredMethod(aliasAnnotation.process(), Field.class);
					method.setAccessible(true);
					method.invoke(this, field);
					return;
				}
			}
		}
	}
	
	private void noBoolean(Field field) throws Exception{
		field.set(this, false);
	}
	
	private void booleanValue(Field field) throws Exception{
		field.set(this, true);
	}
	
	public boolean isRegisterAllow(){
		return this.allowRegister;
	}
	
	public boolean isColorAllow(){
		return this.allowColor;
	}
	
	@Repeatable(Aliases.class)
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Alias{
		
		String value();
		
		String process();
		
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Aliases{
		
		Alias[] value();
		
	}
	
	
}
