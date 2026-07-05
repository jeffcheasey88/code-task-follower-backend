package be.jeffcheasey88.codetaskfollower.model;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Player{

	@Key(auto = true) private int id;
	private String username;
	private String password;
	private boolean isAdmin;
	
	public Player(int id, String username, String password, boolean isAdmin){
		this.id = id;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public boolean getIsAdmin(){
		return this.isAdmin;
	}
}
