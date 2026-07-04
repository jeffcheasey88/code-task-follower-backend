package be.jeffcheasey88.codetaskfollower.configuration;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.exception.HttpError;
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
			System.out.println("parameter "+parameter.getType()+" "+parameter.getName()+" -> "+Arrays.toString(parameter.getAnnotations())+" -> "+Arrays.toString(parameter.getDeclaredAnnotations()));
			if(parameter.getType().getPackage().getName().contains("codetaskfollower.dto")){
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
				Repository<Object,Object> repository = (Repository<Object, Object>) this.dependencies.find(null, Class.forName("be.jeffcheasey88.codetaskfollower.repository." + parameter.getType().getSimpleName() + "Repository"), null);
				for(Annotation annotation : parameter.getAnnotations()){
					System.out.println(" -> "+annotation);
				}
				//Argument key = parameter.getAnnotation(Argument.class); //FIXME System.out.println(List.of(parameter.getAnnotations()).stream().map(a -> a.annotationType().getName()).reduce((acc, s) -> acc + s).get());
				Object model = repository.findById(repository.parseKey(matcher.group(1))); //key.value())));
				if(model == null) throw new HttpError(404);
				parameter.setValue(model);
			}
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.PARAMETER)
	public @interface Argument{
		
		int value() default 1;
		
	}
}
