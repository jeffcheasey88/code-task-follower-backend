package be.jeffcheasey88.codetaskfollower.model;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Code{

	@Key(auto=true) private int id;
	private Branch branch;
	private String classPath;
	
	public Code(int id, Branch branch, String classPath){
		this.id = id;
		this.branch = branch;
		this.classPath = classPath;
	}
}
