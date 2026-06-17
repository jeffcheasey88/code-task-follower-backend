package be.jeffcheasey88.codetaskfollower.model;

import java.util.List;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Chronometer{

	@Key(auto=true) private int id;
	private int seconds;
	private List<ChronomterPart> parts;
	
	
}
