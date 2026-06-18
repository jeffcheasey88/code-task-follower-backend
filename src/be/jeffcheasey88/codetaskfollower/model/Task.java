package be.jeffcheasey88.codetaskfollower.model;

import java.util.List;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Task{

	@Key(auto=true) private int id;
	private String name;
	private State state;
	private Chronometer chronometer;
	private List<Tag> tags;
	private List<Task> dependencies;
	private List<Project> projects;
	private List<Commit> commits;
	private List<Code> codes;
	
}
