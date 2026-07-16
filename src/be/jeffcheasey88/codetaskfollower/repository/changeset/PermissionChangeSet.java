package be.jeffcheasey88.codetaskfollower.repository.changeset;

import be.jeffcheasey88.codetaskfollower.repository.ChangeSetApplier;
import be.jeffcheasey88.codetaskfollower.repository.ChangeSetApplier.LogicalChangeSet;
import dev.peerat.framework.dependency.Injection;

@Injection
public class PermissionChangeSet implements LogicalChangeSet{
	
	public PermissionChangeSet(@Injection ChangeSetApplier changesetApplier){
		changesetApplier.addChangeSet(this);
	}

	@Override
	public int getIndexNumber(){
		return 1;
	}

	@Override
	public void applyBefore(){}

	@Override
	public void apply(){}

	@Override
	public void applyAfter(){
		
	}
}
