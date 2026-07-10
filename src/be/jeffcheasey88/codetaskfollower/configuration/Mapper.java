package be.jeffcheasey88.codetaskfollower.configuration;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.exception.HttpError;
import dev.peerat.framework.Context;
import dev.peerat.framework.HttpReader;
import dev.peerat.framework.HttpWriter;
import dev.peerat.framework.RequestType;
import dev.peerat.framework.json.Json;
import dev.peerat.framework.json.JsonArray;
import dev.peerat.framework.json.JsonMap;
import dev.peerat.framework.routes.ResponseMapper;
import dev.peerat.framework.routes.responses.ExceptionResponse;
import dev.peerat.framework.routes.responses.Response;

public class Mapper implements ResponseMapper, Response, ExceptionResponse{
	
	public Mapper(){
		Json.addConverter(o -> {
			if(o.getClass().getPackage().getName().contains("codetaskfollower.dto")){
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
			if(result instanceof Number){
				context.response(200);
				writer.write(""+(Number)result);
				writer.flush();
				return;
			}
			String value = toJson(result).toString();
			context.response(200, "Content-Type: application/json", "Content-Length: "+value.length());
			writer.write(value);
			writer.flush();
			writer.safeWait(10000);
		}else{
			if(context.getResponseCode() == 0) context.response(200);
		}
	}
	
	public static Json toJson(Object value) throws Exception{
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
	
	@Override
	public void execute(HttpWriter writer, Throwable throwable, RequestType type, String path) throws Exception {
		if (throwable instanceof HttpError error) {
			writer.response(error.getHttpResponseStatusCode(), "Content-Type: application/json");
			
			if (error.isClientError() && error.getClass().getDeclaredFields().length > 0) {
				writer.write(error);
			}
		} else {
			System.err.println("\n\n Error processing request: [" + type + "] " + path);
			throwable.printStackTrace();
		}
	}
	
}
