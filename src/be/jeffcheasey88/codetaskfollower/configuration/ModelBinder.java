package be.jeffcheasey88.codetaskfollower.configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import dev.peerat.framework.Context;
import dev.peerat.framework.HttpReader;
import dev.peerat.framework.HttpWriter;
import dev.peerat.framework.json.JsonMap;
import dev.peerat.framework.routes.binder.ExecutableProvider;
import dev.peerat.framework.routes.binder.Parameter;

public class ModelBinder implements ExecutableProvider{

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
			}
		}
	}

}
