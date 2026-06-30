package be.jeffcheasey88.codetaskfollower.model;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class ChronometerPart{

	@Key(auto=true) private int id;
	private int seconds;
	private String description;
	private Chronometer chronometer;
	
	public ChronometerPart(int id, int seconds, String description, Chronometer chronometer){
		this.id = id;
		this.seconds = seconds;
		this.description = description;
		this.chronometer = chronometer;
	}
	
}
