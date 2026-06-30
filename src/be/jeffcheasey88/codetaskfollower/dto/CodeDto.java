package be.jeffcheasey88.codetaskfollower.dto;

import be.jeffcheasey88.codetaskfollower.validator.RegexValidator.Regex;

public class CodeDto{
	private int id;
	private BranchDto branch;
	@Regex("\\d+") private String classPath;
	public CodeDto(int id, BranchDto branch, String classPath){
		this.id = id;
		this.branch = branch;
		this.classPath = classPath;
	}
}
