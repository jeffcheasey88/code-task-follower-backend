package be.jeffcheasey88.codetaskfollower.configuration;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.regex.Matcher;

import dev.peerat.framework.Context;
import dev.peerat.framework.HttpReader;
import dev.peerat.framework.HttpWriter;
import dev.peerat.framework.json.Json;
import dev.peerat.framework.json.JsonArray;
import dev.peerat.framework.json.JsonMap;
import dev.peerat.framework.routes.ResponseMapper;
import dev.peerat.framework.routes.responses.Response;

public class Mapper implements ResponseMapper, Response{
	
	public Mapper(){
		Json.addConverter(o -> {
			if(o.getClass().getPackage().getName().contains("codetaskfollower.model")){
				try {
					return toJson(o).toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		});
	}

	@Override
	public void map(Matcher matcher, Context context, HttpReader reader, HttpWriter writer, Object result) throws Exception{
		if(result != null){
			String value = toJson(result).toString();
			context.response(200, "Content-Type: application/json", "Content-Length: "+value.length());
			writer.write(value);
			writer.flush();
			writer.safeWait(10000);
		}else{
			if(context.getResponseCode() == 0) context.response(200);
		}
	}
	
	private static Json toJson(Object value) throws Exception{
		if(value instanceof Collection){
			return new JsonArray((Collection<Object>)value);
		}
		JsonMap json = new JsonMap();
		for(Field field : value.getClass().getDeclaredFields()){
			field.setAccessible(true);
			Object fieldValue = field.get(value);
			if(fieldValue == null) continue;
			json.set(field.getName(), fieldValue);
		}
		return json;
	}
	

	@Override
	public void execute(Matcher matcher, Context context, HttpReader reader, HttpWriter writer) throws Exception{
		if(context.getResponseCode() == 0) context.response(200);
	}
	
}
