package be.jeffcheasey88.codetaskfollower.model;

import be.jeffcheasey88.codetaskfollower.validator.MinValidator.Min;
import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class ChronometerPart extends Model {

	@Key(auto=true) private int id;
	@Min(0)
	private int seconds;
	private String description;
	private int chronometerId;
//	private Chronometer chronometer;
	
	public ChronometerPart(int id, int seconds, String description, int chronometerId){
		this.id = id;
		this.seconds = seconds;
		this.description = description;
		this.chronometerId = chronometerId;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setSeconds(int seconds){
		this.seconds = check("seconds", seconds);
	}
	
	public int getSeconds(){
		return this.seconds;
	}
	
	public void setDescription(String description){
		this.description = check("description", description);
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public int getChronometerId(){
		return this.chronometerId;
	}
}
