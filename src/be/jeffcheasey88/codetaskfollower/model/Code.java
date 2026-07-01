package be.jeffcheasey88.codetaskfollower.model;

import be.jeffcheasey88.codetaskfollower.validator.MinValidator.Min;
import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Code extends Model {

	@Key(auto=true) private int id;
	private Branch branch;
	@Min
	private String classPath;
	
	public Code(int id, Branch branch, String classPath){
		this.id = id;
		this.branch = branch;
		this.classPath = classPath;
	}
	
	public void setClassPath(String classPath){
		this.classPath = check("classPath", classPath);
	}
}
