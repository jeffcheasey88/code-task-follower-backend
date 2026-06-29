package be.jeffcheasey88.codetaskfollower.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.exceptions.NotFoundException;
import be.jeffcheasey88.codetaskfollower.repository.Repository;
import dev.peerat.framework.Context;
import dev.peerat.framework.HttpReader;
import dev.peerat.framework.HttpWriter;
import dev.peerat.framework.dependency.DependencyInjector;
import dev.peerat.framework.json.JsonMap;
import dev.peerat.framework.routes.binder.ExecutableProvider;
import dev.peerat.framework.routes.binder.Parameter;

public class ModelBinder implements ExecutableProvider{
	
	private DependencyInjector dependencies;
	
	public ModelBinder(DependencyInjector dependencies){
		this.dependencies = dependencies;
	}

	@Override
	public void provide(Matcher matcher, Context context, HttpReader reader, HttpWriter writer, Method method, Parameter[] parameters) throws Exception{
		for(Parameter parameter : parameters){
			if(parameter.getType().getPackage().getName().contains("codetaskfollower.model.dto")){
				JsonMap json = reader.readJson();
				Constructor<?> constructor = null;
				for(Constructor<?> declardConstructor : parameter.getType().getDeclaredConstructors()){
					if(Modifier.isPublic(declardConstructor.getModifiers())){
						if(constructor == null || declardConstructor.getParameterCount() > constructor.getParameterCount()){
							constructor = declardConstructor;
						}
					}
				}
				List<Object> values = new ArrayList<>(constructor.getParameterCount());
				for(java.lang.reflect.Parameter constructorParameter : constructor.getParameters()){
					values.add(json.get(constructorParameter.getName()));
				}
				parameter.setValue(constructor.newInstance(values.toArray()));
			}else if(parameter.getType().getPackage().getName().endsWith("codetaskfollower.model")){
				Repository<Object,Object> repository = (Repository<Object, Object>) this.dependencies.find(null, Class.forName("be.jeffcheasey88.codetaskfollower.repository."+parameter.getType()+"Repository"), null);
				Key key = parameter.getAnnotation(Key.class);
				Object model = repository.findById(repository.parseKey(matcher.group(key.value())));
				if(model == null) throw new NotFoundException();
				parameter.setValue(model);
			}
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.PARAMETER)
	public static @interface Key{
		
		int value() default 1;
		
	}
}
