package be.jeffcheasey88.codetaskfollower.dto;

public class CodeDto{
	private int id;
	private BranchDto branch;
	private String classPath;
	public CodeDto(int id, BranchDto branch, String classPath){
		this.id = id;
		this.branch = branch;
		this.classPath = classPath;
	}
}
