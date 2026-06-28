package be.jeffcheasey88.codetaskfollower.model.dto;

public class BranchDto{
	private int id;
	private String repositoryName;
	private String branchName;
	public BranchDto(int id, String repositoryName, String branchName){
		this.id = id;
		this.repositoryName = repositoryName;
		this.branchName = branchName;
	}
}
