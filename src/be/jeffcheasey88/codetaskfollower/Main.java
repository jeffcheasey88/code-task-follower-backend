package be.jeffcheasey88.codetaskfollower;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.GET;
import static dev.peerat.framework.RequestType.OPTIONS;
import static dev.peerat.framework.RequestType.PATCH;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import java.lang.reflect.Method;
import java.util.Iterator;

import be.jeffcheasey88.codetaskfollower.configuration.Authenticator;
import be.jeffcheasey88.codetaskfollower.configuration.Authenticator.User;
import be.jeffcheasey88.codetaskfollower.configuration.DatabaseConfiguration;
import be.jeffcheasey88.codetaskfollower.configuration.Mapper;
import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder;
import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository;
import dev.peerat.framework.Locker;
import dev.peerat.framework.RequestType;
import dev.peerat.framework.Router;
import dev.peerat.framework.auth.AuthException;
import dev.peerat.framework.dependency.DependencyInjector;
import dev.peerat.framework.routes.RouteState;
import dev.peerat.mapping.Ship;
import dev.peerat.mapping.providers.mysql.MySQLCompass;

public class Main{
	
	private static final String COLOR_RESET  = "\u001B[0m";
	private static final String COLOR_RED    = "\u001B[31m";
	private static final String COLOR_GREEN  = "\u001B[32m";
	private static final String COLOR_YELLOW = "\u001B[33m";
	private static final String COLOR_BLUE   = "\u001B[34m";

	public static void main(String[] args) throws Exception{
		boolean acceptColor = true;
		if(args != null && args.length > 0 && args[0].equalsIgnoreCase("no-color")){
			acceptColor = false;
		}
		
		Ship ship = new Ship("mysql", new MySQLCompass("database", 3306, "code-task-follower", "root", "root"), new DatabaseConfiguration());
		ship.setSails();
		TemporalRepository.INSTANCE.connector(ship); //TODO remove
		
		Router router = new Router();
		
		router.activeReOrdering();
		router.addDefaultHeader("Access-Control-Allow-Origin: *", GET, PUT, PATCH, POST, DELETE, OPTIONS);
		router.addDefaultHeaders(OPTIONS, "Access-Control-Allow-Methods: *","Access-Control-Allow-Headers: *");
		
		router.setDefaultResponse((matcher, context, reader, writer) -> context.response(context.getType().equals(OPTIONS) ? 200 : 404));
		
		DependencyInjector injector = new DependencyInjector()
				.of(router)
				.of(Locker.class, Locker::new)
				.ofServices();
		
		Mapper mapper = new Mapper();
		router.setMapper(mapper);
		router.setInternalErrorResponse(mapper);
		
		router.setAuthenticator(new Authenticator());
		router.bind(new ModelBinder(injector));
		
		final boolean colorAccepted = acceptColor;
		new Thread(() -> router.getLogger().listen(context -> {
				RequestType type = context.getType();
				if(type.equals(RequestType.OPTIONS)) return;
				String prefix = "";
				if(context.isLogged()){
					if(colorAccepted) prefix+=COLOR_BLUE;
					prefix += "("+context.<User>getUser().getName()+")";
					if(colorAccepted) prefix+=COLOR_RESET;
					prefix+=" ";
				}
				int responseCode = context.getResponseCode();
				if(colorAccepted){
					if(responseCode >= 200 && responseCode < 300){
						System.out.println(prefix+COLOR_GREEN+"["+type+"] "+context.getPath()+COLOR_RESET);
					}else if(responseCode == 0 || responseCode >= 500){
						System.out.println(prefix+"["+type+"] "+context.getPath()+" -> "+COLOR_RED+""+responseCode+""+COLOR_RESET);
					}else{
						System.out.println(prefix+"["+type+"] "+context.getPath()+" -> "+COLOR_YELLOW+""+responseCode+""+COLOR_RESET);
					}
				}else{
					System.out.println(prefix+"["+type+"] "+context.getPath()+" -> "+responseCode);
				}
			}, Throwable::printStackTrace
		)).start();
		
		new Thread(() ->  router.getExceptionLogger().listen(throwable -> {
			if(throwable instanceof AuthException){
				System.err.println("erreur d'authentification");
				return;
			}
			throwable.printStackTrace();
		}, Throwable::printStackTrace)).start();
		
		router.registerPackages("be.jeffcheasey88.codetaskfollower.controller", injector);
		
		System.out.println("routes:");
		Iterator<RouteState> routeIterator = router.routeIterator();
		while(routeIterator.hasNext()){
			RouteState route = routeIterator.next();
			String type = route.isWebSocket() ? "WS" : route.getRoute().type().toString();
			String returnType = route.isVoidMethod() ? "" : " -> "+toReturnString(route.getMethod());
			System.out.println("\t["+type+"] "+route.getRoute().path()+returnType+" needLogin:"+route.getRoute().needLogin());
		}
		System.out.println();
		
		System.out.println("so "+System.getenv("TERM"));
		
		router.listen(8001, false);
	}
	
	private static String toReturnString(Method method){
		Class<?> clazz = method.getReturnType();
		if(clazz.getGenericInterfaces().length > 0) return method.getGenericReturnType().toString();
		return clazz.getSimpleName();
	}
}
