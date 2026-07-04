package be.jeffcheasey88.codetaskfollower.dto;

public class CodeDto{
	private Integer id;
	private BranchDto branch;
	private String classPath;
	public CodeDto(Integer id, BranchDto branch, String classPath){
		this.id = id;
		this.branch = branch;
		this.classPath = classPath;
	}
}
