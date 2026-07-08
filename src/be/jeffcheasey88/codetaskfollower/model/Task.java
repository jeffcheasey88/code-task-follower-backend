package be.jeffcheasey88.codetaskfollower.model;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.validator.MinValidator.Min;
import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Task extends Model{

	@Key(auto=true) private int id;
	
	@Min
	private String name;
	//private State state;
	//private Chronometer chronometer;
	private List<Tag> tags;
	private List<Task> dependencies;
	private List<Project> projects;
	private List<Commit> commits;
	private List<Branch> branches;
	private List<Code> codes;
	
	public Task(int id, String name, 
			//State state, Chronometer chronometer,
			List<Tag> tags, List<Task> dependencies, List<Project> projects, List<Commit> commits, List<Branch> branches, List<Code> codes){
		this.id = id;
		this.name = name;
//		this.state = state;
//		this.chronometer = chronometer;
		this.tags = tags;
		this.dependencies = dependencies;
		this.projects = projects;
		this.commits = commits;
		this.branches = branches;
		this.codes = codes;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public List<Task> getDependencies() {
		return dependencies;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public List<Commit> getCommits() {
		return commits;
	}
	public List<Branch> getBranches() {
		return branches;
	}
	public List<Code> getCodes() {
		return codes;
	}

	public void setName(String name) {
		this.name = check("name", name);
	}
}
