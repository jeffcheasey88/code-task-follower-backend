package be.jeffcheasey88.codetaskfollower.repository.changeset;

import static be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.jeffcheasey88.codetaskfollower.model.Project;
import be.jeffcheasey88.codetaskfollower.model.State;
import be.jeffcheasey88.codetaskfollower.repository.ChangeSetApplier;
import be.jeffcheasey88.codetaskfollower.repository.ChangeSetApplier.LogicalChangeSet;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.mapping.TreasureCache;

@Injection
public class StateChangeSet implements LogicalChangeSet{
	
	public StateChangeSet(@Injection ChangeSetApplier changesetApplier){
		changesetApplier.addChangeSet(this);
	}

	@Override
	public int getIndexNumber(){
		return 2;
	}

	@Override
	public void applyBefore(){}

	@Override
	public void apply(){
		Set<Integer> states = new HashSet<>();
		List<Project> projects = TreasureCache.<Project>selectAll().toList();
		for(Project project : projects){
			List<State> projectStates = selectStates(project.getId());
			for(State state : projectStates){
				if(states.add(state.getId())) continue;
				removeProjectStates(project, state);
				State copy = new State(0, state.getName(), state.getColor());
				insertProjectStates(project, copy);
				states.add(copy.getId());
			}
		}
	}

	@Override
	public void applyAfter(){}

}
