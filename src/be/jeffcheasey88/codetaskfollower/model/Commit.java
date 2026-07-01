package be.jeffcheasey88.codetaskfollower.model;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.validator.MinValidator.Min;
import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Commit extends Model {

	@Key(auto=true) private int id;
	@Min
	private String hash;
	private List<Task> tasks;
	
	public Commit(int id, String hash, List<Task> tasks){
		this.id = id;
		this.hash = hash;
		this.tasks = tasks;
	}
	
	public void setHash(String hash){
		this.hash = check("hash", hash);
	}
	
}
