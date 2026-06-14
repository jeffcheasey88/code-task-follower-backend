package be.jeffcheasey88.codetaskfollower.model;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;

@Treasure
public class Branch{

	@Key private int id;
	private String repositoryName;
	private String branchName;
	
}
