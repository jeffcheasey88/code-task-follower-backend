package be.jeffcheasey88.codetaskfollower.model;

import be.jeffcheasey88.codetaskfollower.validator.MinValidator.Min;
import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Branch extends Model{

	@Key(auto=true) private int id;
	@Min
	private String repositoryName;
	private String branchName;
	
	public Branch(int id, String repositoryName, String branchName){
		this.id = id;
		this.repositoryName = repositoryName;
		this.branchName = branchName;
	}
	
	public void setRepositoryName(String repositoryName){
		this.repositoryName = check("repositoryName", repositoryName);
	}
	
	public void setBranchName(String branchName){
		this.branchName = check("branchName", branchName);
	}
}
