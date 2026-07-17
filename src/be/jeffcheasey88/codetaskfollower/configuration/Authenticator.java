package be.jeffcheasey88.codetaskfollower.configuration;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;

import dev.peerat.framework.auth.JwtAuthenticator;

public class Authenticator extends JwtAuthenticator{

	public Authenticator() throws Exception{
		configure(
			builder -> builder.setExpectedIssuer("localhost"),
			(JwtClaims claims, User user) -> {
				claims.setIssuer("localhost");
				claims.setExpirationTimeMinutesInTheFuture(3600);
				user.write(claims);
			},
			claims -> {
				try{
					return new User(claims);
				}catch(Exception e){
					throw new RuntimeException(e);
				}
			}
		);
	}
	
	
	public static class User extends dev.peerat.framework.auth.User{
		
		private int id;
		private boolean isAdmin;
		private String name;
		
		public User(JwtClaims claims) throws MalformedClaimException{
			this.id = ((Number)claims.getClaimValue("id")).intValue();
			this.isAdmin = ((Boolean)claims.getClaimValue("admin")).booleanValue();
			this.name = claims.getStringClaimValue("name");
		}
		
		public User(int id, String name, boolean isAdmin){
			this.id = id;
			this.name = name;
			this.isAdmin = isAdmin;
		}
		
		public int getId(){
			return this.id;
		}
		
		public String getName(){
			return this.name;
		}
		
		public boolean isAdmin(){
			return this.isAdmin;
		}
		
		public void write(JwtClaims claims){
			claims.setClaim("id", id);
			claims.setStringClaim("name", name);
			claims.setClaim("admin", isAdmin);
		}
	}
}
