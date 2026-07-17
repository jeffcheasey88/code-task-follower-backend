package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.*;

import com.password4j.Password;

import be.jeffcheasey88.codetaskfollower.configuration.Authenticator.User;
import be.jeffcheasey88.codetaskfollower.configuration.Configuration;
import be.jeffcheasey88.codetaskfollower.dto.AuthDto;
import be.jeffcheasey88.codetaskfollower.dto.UpdatePasswordDto;
import be.jeffcheasey88.codetaskfollower.model.Player;
import dev.peerat.framework.Context;
import dev.peerat.framework.Router;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class AuthController{
	
	@Injection private Router router;
	@Injection private Configuration config;
	
	@Route(path = "/users/register", type = POST)
	public void register(Context context, AuthDto authDto) throws Exception{
		if(!config.isRegisterAllow()){
			context.response(400);
			return;
		}
		Player player = getPlayer(authDto.username());
		if(player != null){
			context.response(400);
			return;
		}
		player = new Player(0, authDto.username(), Password.hash(authDto.password()).withArgon2().getResult(), false);
		context.response(200,
				"Access-Control-Expose-Headers: Authorization",
				"Authorization: Bearer " + this.router.createAuthUser(new User(player.getId(), authDto.username(), player.getIsAdmin())));
	}
	
	private Player getPlayer(String username){
		return TreasureCache.<Player>selectAll().filter(player -> player.getUsername() == username).get();
	}
	
	@Route(path = "/users/login", type = POST)
	public void login(Context context, AuthDto authDto) throws Exception{
		Player player = getPlayer(authDto.username());
		if(player == null){
			context.response(400);
			return;
		}
		if(Password.check(authDto.password(), player.getPassword()).withArgon2()){
			context.response(200,
					"Access-Control-Expose-Headers: Authorization",
					"Authorization: Bearer " + this.router.createAuthUser(new User(player.getId(), authDto.username(), player.getIsAdmin())));
		}else{
			context.response(400);
		}
	}
	
	@Route(path = "/users/changepw", type = PATCH, needLogin = true)
	public void changePassword(User user, UpdatePasswordDto updatePasswordDto) throws Exception{
		Player player = getPlayer(user.getName());
		if(Password.check(updatePasswordDto.oldPassword(), player.getPassword()).withArgon2()){
			player.setPassword(Password.hash(updatePasswordDto.newPassword()).withArgon2().getResult());
		}else{
			throw new NullPointerException();
		}
	}
}
