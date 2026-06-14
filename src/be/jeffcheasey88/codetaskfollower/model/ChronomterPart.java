package be.jeffcheasey88.codetaskfollower.model;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class ChronomterPart{

	@Key private int id;
	private int seconds;
	private String description;
	
}
