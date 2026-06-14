package be.jeffcheasey88.codetaskfollower.model;

import java.util.List;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Project{

	@Key private int id;
	private String name;
	private String color;
	private List<Tag> tags;
	private List<Task> tasks;
	private List<Branch> branches;
	
}
