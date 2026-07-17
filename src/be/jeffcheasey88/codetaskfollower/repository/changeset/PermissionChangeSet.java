package be.jeffcheasey88.codetaskfollower.repository.changeset;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.model.Task;
import be.jeffcheasey88.codetaskfollower.model.Player;
import be.jeffcheasey88.codetaskfollower.model.Project;
import be.jeffcheasey88.codetaskfollower.repository.ChangeSetApplier;
import be.jeffcheasey88.codetaskfollower.repository.ChangeSetApplier.LogicalChangeSet;
import be.jeffcheasey88.codetaskfollower.tmp.Permission;
import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository;
import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository.SqlParam;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.mapping.TreasureCache;

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
		List<Player> players = TreasureCache.<Player>selectAll().toList();
		List<Project> projects = TreasureCache.<Project>selectAll().toList();
		List<Task> tasks = TreasureCache.<Task>selectAll().toList();
		for(Player player : players){
			for(Project project : projects){
				TemporalRepository.INSTANCE.externalUpdateRequest(
						"INSERT INTO ProjectAccess(roleType, roleId, projectId, accessLevel) VALUES (?,?,?,?)",
						new SqlParam("String","player"),
						new SqlParam("int",player.getId()),
						new SqlParam("int",project.getId()),
						new SqlParam("int", Permission.PERM_ADMIN)
					);
			}
			for(Task task : tasks){
				TemporalRepository.INSTANCE.externalUpdateRequest(
						"INSERT INTO TaskAccess(roleType, roleId, taskId, accessLevel) VALUES (?,?,?,?)",
						new SqlParam("String","player"),
						new SqlParam("int",player.getId()),
						new SqlParam("int",task.getId()),
						new SqlParam("int", Permission.PERM_ADMIN)
					);
			}
		}
	}
}
