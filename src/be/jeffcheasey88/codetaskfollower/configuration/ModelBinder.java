package be.jeffcheasey88.codetaskfollower.configuration;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.exception.HttpError;
import be.jeffcheasey88.codetaskfollower.repository.Repository;
import dev.peerat.framework.Context;
import dev.peerat.framework.HttpReader;
import dev.peerat.framework.HttpWriter;
import dev.peerat.framework.dependency.DependencyInjector;
import dev.peerat.framework.json.JsonArray;
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
			if(parameter.getType().getPackage().getName().contains("codetaskfollower.dto")){
				parameter.setValue(toDto(reader.readJson(), parameter.getType()));
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
	
	private Object toDto(JsonMap json, Class<?> type) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Constructor<?> constructor = null;
		for(Constructor<?> declardConstructor : type.getDeclaredConstructors()){
			if(Modifier.isPublic(declardConstructor.getModifiers())){
				if(constructor == null || declardConstructor.getParameterCount() > constructor.getParameterCount()){
					constructor = declardConstructor;
				}
			}
		}
		List<Object> values = new ArrayList<>(constructor.getParameterCount());
		for(java.lang.reflect.Parameter constructorParameter : constructor.getParameters()){
			Object value = json.get(constructorParameter.getName());
			if(value != null){
				Class<?> constructorType = constructorParameter.getType();
				if(constructorType.equals(Integer.class) || constructorType.equals(Integer.TYPE)){
					value = ((Number)value).intValue();
				}else if(Collection.class.isAssignableFrom(constructorType)){
					Collection<Object> collection = new LinkedList<>();
					JsonArray array = (JsonArray)value;
					value = collection;
					Type genericType = constructorParameter.getParameterizedType();
					if(genericType instanceof ParameterizedType){
						ParameterizedType parameterizedType = (ParameterizedType)genericType;
						Class<?> listType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
						for(Object element : array.toCollection()) collection.add(toDto((JsonMap) element, listType));
					}
				}
			}
			values.add(value);
		}
		return constructor.newInstance(values.toArray());
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.PARAMETER)
	public @interface Argument{
		
		int value() default 1;
		
	}
}
