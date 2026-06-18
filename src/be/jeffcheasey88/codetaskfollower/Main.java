package be.jeffcheasey88.codetaskfollower;

import static dev.peerat.framework.RequestType.*;

import be.jeffcheasey88.codetaskfollower.configuration.DatabaseConfiguration;
import be.jeffcheasey88.codetaskfollower.configuration.Mapper;
import dev.peerat.framework.Router;
import dev.peerat.framework.dependency.DependencyInjector;
import dev.peerat.mapping.Ship;
import dev.peerat.mapping.providers.mysql.MySQLCompass;

public class Main{

	public static void main(String[] args) throws Exception{
		
		Ship ship = new Ship("mysql", new MySQLCompass("database", 3306, "code-task-follower", "root", "root"), new DatabaseConfiguration());
		ship.setSails();
		
		Router router = new Router();
		
		router.activeReOrdering();
		router.addDefaultHeader("Access-Control-Allow-Origin: *", GET, PUT, PATCH, POST, DELETE, OPTIONS);
		router.addDefaultHeaders(OPTIONS, "Access-Control-Allow-Methods: *","Access-Control-Allow-Headers: *");
		
		router.setDefaultResponse((matcher, context, reader, writer) -> context.response(context.getType().equals(OPTIONS) ? 200 : 404));
		
		router.setMapper(new Mapper());
		
		new Thread(new Runnable(){
			public void run(){
				router.getLogger().listen((context) -> {
					System.out.println("["+(("?" +context.getType()+" "+context.getPath()+" -> "+context.getResponseCode())));
				},
				(e) -> e.printStackTrace()
				);
				
			}
		}).start();
		
		new Thread(() ->  router.getExceptionLogger().listen((throwable) ->  throwable.printStackTrace(), (e) -> e.printStackTrace())).start();
		
		
		DependencyInjector injector = new DependencyInjector();
		router.registerPackages("be.jeffcheasey88.codetaskfollower.controller", injector);
		router.listen(8001, false);
	}
	
}
