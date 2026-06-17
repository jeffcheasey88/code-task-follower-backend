package be.jeffcheasey88.codetaskfollower.configuration;

import dev.peerat.mapping.providers.mysql.MySQLMap;

public class DatabaseConfiguration extends MySQLMap{
	
	public DatabaseConfiguration(){
		link(javaClazz->javaClazz.toLowerCase()+"s");
		
		
	}

}
