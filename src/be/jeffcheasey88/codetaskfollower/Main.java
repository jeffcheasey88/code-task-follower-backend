package be.jeffcheasey88.codetaskfollower;

import static dev.peerat.framework.RequestType.*;

import dev.peerat.framework.Router;
import dev.peerat.framework.dependency.DependencyInjector;
import dev.peerat.mapping.Ship;
import dev.peerat.mapping.providers.mysql.MySQLCompass;
import dev.peerat.mapping.providers.mysql.MySQLMap;

public class Main{

	public static void main(String[] args) throws Exception{
		
		Ship ship = new Ship("mysql", new MySQLCompass(null, 0, null, null, null), new MySQLMap());
		ship.setSails();
		
		Router router = new Router();
		
		router.activeReOrdering();
		router.addDefaultHeader("Access-Control-Allow-Origin: *", GET, PUT, PATCH, POST, DELETE, OPTIONS);
		router.addDefaultHeaders(OPTIONS, "Access-Control-Allow-Methods: *","Access-Control-Allow-Headers: *");
		
		router.setDefaultResponse((matcher, context, reader, writer) -> context.response(context.getType().equals(OPTIONS) ? 200 : 404));
		
		DependencyInjector injector = new DependencyInjector();
		router.registerPackages("be.jeffcheasey88.codetaskfollower.controller", injector);
		router.listen(80, false);
	}
	
}
