package be.jeffcheasey88.codetaskfollower.model;

import java.util.List;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Commit{

	@Key(auto=true) private int id;
	private String hash;
	private List<Task> tasks;
	
}
