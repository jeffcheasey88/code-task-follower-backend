package be.jeffcheasey88.codetaskfollower.model;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Code{

	@Key private int id;
	private Branch branch;
	private String classPath;
	
}
