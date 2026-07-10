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

	public static void main(String[] args) throws Exception{
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
		
		new Thread(() -> router.getLogger().listen(context -> {
				RequestType type = context.getType();
				if(type.equals(RequestType.OPTIONS)) return;
				String prefix = "";
				if(context.isLogged()) prefix = "("+context.<User>getUser().getName()+") ";
				System.out.println(prefix+"["+type+"] "+context.getPath()+" -> "+context.getResponseCode());
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
		
		router.listen(8001, false);
	}
	
	private static String toReturnString(Method method){
		Class<?> clazz = method.getReturnType();
		if(clazz.getGenericInterfaces().length > 0) return method.getGenericReturnType().toString();
		return clazz.getSimpleName();
	}
}
