package be.jeffcheasey88.codetaskfollower.dto;

public class BranchDto{
	private Integer id;
	private String repositoryName;
	private String branchName;
	public BranchDto(Integer id, String repositoryName, String branchName){
		this.id = id;
		this.repositoryName = repositoryName;
		this.branchName = branchName;
	}
}
