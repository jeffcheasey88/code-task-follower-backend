package be.jeffcheasey88.codetaskfollower.model;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.validator.MinValidator.Min;
import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Chronometer extends Model {

	@Key(auto=true) private int id;
	@Min(0)
	private int seconds;
	private int taskId;
	private List<ChronometerPart> parts;
	
	public Chronometer(int id, int seconds, int taskId, List<ChronometerPart> parts){
		this.id = id;
		this.seconds = seconds;
		this.taskId = taskId;
		this.parts = parts;
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
	
	public int getTaskId(){
		return this.taskId;
	}
	
	public List<ChronometerPart> getParts(){
		return this.parts;
	}
}
