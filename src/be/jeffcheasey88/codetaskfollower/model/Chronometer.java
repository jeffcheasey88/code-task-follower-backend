package be.jeffcheasey88.codetaskfollower.model;

import java.util.List;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Chronometer{

	@Key(auto=true) private int id;
	private int seconds;
	private List<ChronometerPart> parts;
	
	public Chronometer(int id, int seconds, List<ChronometerPart> parts){
		this.id = id;
		this.seconds = seconds;
		this.parts = parts;
	}
}
