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
	private List<Branch> branches;
	private List<Code> codes;
	
	public Task(int id, String name, State state, Chronometer chronometer, List<Tag> tags, List<Task> dependencies, List<Project> projects, List<Commit> commits, List<Branch> branches, List<Code> codes){
		this.id = id;
		this.name = name;
		this.state = state;
		this.chronometer = chronometer;
		this.tags = tags;
		this.dependencies = dependencies;
		this.projects = projects;
		this.commits = commits;
		this.branches = branches;
		this.codes = codes;
	}
	
}
