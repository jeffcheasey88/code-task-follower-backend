package be.jeffcheasey88.codetaskfollower.configuration;

import java.util.regex.Matcher;

import dev.peerat.framework.Context;
import dev.peerat.framework.HttpReader;
import dev.peerat.framework.HttpWriter;
import dev.peerat.framework.routes.ResponseMapper;
import dev.peerat.framework.routes.responses.Response;

public class Mapper implements ResponseMapper, Response{

	@Override
	public void map(Matcher matcher, Context context, HttpReader reader, HttpWriter writer, Object result) throws Exception{
		context.response(200);
		
		if(result != null){
			
		}
	}

	@Override
	public void execute(Matcher matcher, Context context, HttpReader reader, HttpWriter writer) throws Exception{
		
	}
	
}
