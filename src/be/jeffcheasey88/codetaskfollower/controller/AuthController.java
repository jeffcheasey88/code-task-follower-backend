package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.POST;

import be.jeffcheasey88.codetaskfollower.configuration.Authenticator.User;
import be.jeffcheasey88.codetaskfollower.dto.AuthDto;
import dev.peerat.framework.Context;
import dev.peerat.framework.Router;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;

public class AuthController{
	
	@Injection private Router router;
	
	@Route(path = "/users/register", type = POST)
	public void register(Context context, AuthDto authDto) throws Exception{
		context.response(200,
				"Access-Control-Expose-Headers: Authorization",
				"Authorization: Bearer " + this.router.createAuthUser(new User(0, authDto.username())));
	}
	
	@Route(path = "/users/login", type = POST)
	public void login(Context context, AuthDto authDto) throws Exception{
		context.response(200,
				"Access-Control-Expose-Headers: Authorization",
				"Authorization: Bearer " + this.router.createAuthUser(new User(0, authDto.username())));
	}
}
