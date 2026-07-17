package be.jeffcheasey88.codetaskfollower.controller;

import static be.jeffcheasey88.codetaskfollower.tmp.Permission.*;
import static dev.peerat.framework.RequestType.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.configuration.Authenticator.User;
import be.jeffcheasey88.codetaskfollower.exception.HttpError;
import be.jeffcheasey88.codetaskfollower.model.Player;
import be.jeffcheasey88.codetaskfollower.tmp.Permission;
import dev.peerat.framework.data.QueryParameters;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class PermissionController{
	
	/*
	 * 
	 * 	 GET /players
	 * 	 GET /players?name=
	 * 
	 *   GET /players/id
	 *   GET /groups/
	 *   GET /groups/id
	 *   POST /groups/
	 *   POST /groups/id
	 *   PUT /groups/id
	 *   PUT /groups/id/players/id
	 *   DELETE /groups/id
	 *   DELETE /groups/id/players/id
	 *   
	 * 
	 *	 GET /projects/id/players
	 *	 GET /projects/id/groups
	 *	 GET /projects/id/players/id
	 *	 GET /projects/id/groups/id
	 *	 POST /projects/id/players
	 *	 POST /projects/id/groups
	 *	 PUT /projects/id/players
	 *   PUT /projects/id/groups
	 *   DELETE /projects/id/players/id
	 *   DELETE /projects/id/groups/id
	 *   
	 * 	 GET /tasks/id/players
	 *	 GET /tasks/id/groups
	 *	 GET /tasks/id/players/id
	 *	 GET /tasks/id/groups/id
	 *	 POST /tasks/id/players
	 *	 POST /tasks/id/groups
	 *	 PUT /tasks/id/players/id
	 *   PUT /tasks/id/groups/id
	 *   DELETE /tasks/id/players/id
	 *   DELETE /tasks/id/groups/id
	 */
	
	record PlayerTreasurePermission(String type, int id, boolean read, boolean add, boolean update, boolean delete, boolean admin){}
	record PlayerPermission(int id, String name, boolean read, boolean add, boolean update, boolean delete, boolean admin){}
	record PlayerPermissionDto(int playerId, boolean add, boolean update, boolean delete, boolean admin){}

	@Route(path="/players", needLogin = true)
	public List<String> getPlayers(){
		return TreasureCache.<Player>selectAll().toList().stream().map(Player::getUsername).toList();
	}
	
	@Route(path="/players"+QueryParameters.QUERY_REGEX, needLogin = true)
	public List<String> getPlayersByName(Matcher matcher) throws UnsupportedEncodingException{
		QueryParameters parameters = new QueryParameters(matcher.group(1));
		String nameQuery = parameters.getValue("name");
		if(nameQuery == null) throw new HttpError(400);
		String query = nameQuery.toLowerCase();
		return TreasureCache.<Player>selectAll().toList().stream().map(Player::getUsername).filter(name -> name.toLowerCase().contains(query)).toList();
	}
	
	@Route(path = "/players/(\\d+)", needLogin = true)
	public List<PlayerTreasurePermission> getPlayerPermission(User user, Matcher matcher){
		if(!user.isAdmin()) throw new HttpError(403);
		
		int id = Integer.parseInt(matcher.group(1));
		return Permission.getEntityPermissions("player", id).stream().map(perm ->
			new PlayerTreasurePermission(
					perm.entityType(),
					perm.entityId(),
					true,
					canAccess(perm.accessLevel(), Permission.PERM_ADD),
					canAccess(perm.accessLevel(), Permission.PERM_UPDATE),
					canAccess(perm.accessLevel(), Permission.PERM_DELETE),
					canAccess(perm.accessLevel(), Permission.PERM_ADMIN)
			)
		).toList();
	}
	
	@Route(path = "/projects/(\\d+)/players", needLogin = true)
	public List<PlayerPermission> getProjectPlayers(User user, Matcher matcher){
		int projectId = Integer.parseInt(matcher.group(1));
		if(!ProjectController.canAdminProject(user, projectId)) throw new HttpError(403);
		return Permission.getEntityPermissions("player", "project", projectId).stream().map(perm -> {
			return new PlayerPermission(
					perm.entityId(),
					getUsername(perm.roleId()),
					true,
					canAccess(perm.accessLevel(), Permission.PERM_ADD),
					canAccess(perm.accessLevel(), Permission.PERM_UPDATE),
					canAccess(perm.accessLevel(), Permission.PERM_DELETE),
					canAccess(perm.accessLevel(), Permission.PERM_ADMIN)
			);
		}
		).toList();
	}
	
	@Route(path = "/projects/(\\d+)/players/(\\d+)", needLogin = true)
	public PlayerPermission getProjectPlayer(User user, Matcher matcher){
		int projectId = Integer.parseInt(matcher.group(1));
		if(!ProjectController.canAdminProject(user, projectId)) throw new HttpError(403);
		int playerId = Integer.parseInt(matcher.group(2));
		int access = Permission.getAccess("player", playerId, "Project", projectId);
		if(access < 0) throw new HttpError(404);
		return new PlayerPermission(
				playerId,
				getUsername(playerId),
				true,
				canAccess(access, Permission.PERM_ADD),
				canAccess(access, Permission.PERM_UPDATE),
				canAccess(access, Permission.PERM_DELETE),
				canAccess(access, Permission.PERM_ADMIN)
			);
	}
	
	@Route(path = "/projects/(\\d+)/players", needLogin = true, type = POST)
	public void addProjectPlayer(User user, Matcher matcher, PlayerPermissionDto permissionDto){
		int projectId = Integer.parseInt(matcher.group(1));
		if(!ProjectController.canAdminProject(user, projectId)) throw new HttpError(403);
		if(Permission.getAccess("player", permissionDto.playerId(), "Project", projectId) >= 0) throw new HttpError(400);
		int access = 0;
		if(permissionDto.add()) access+=Permission.PERM_ADD;
		if(permissionDto.update()) access+=Permission.PERM_UPDATE;
		if(permissionDto.delete()) access+=Permission.PERM_DELETE;
		if(permissionDto.admin()) access+=Permission.PERM_ADMIN;
		Permission.giveAccess("Project", projectId, "player", permissionDto.playerId(), access);
	}
	
	@Route(path = "/projects/(\\d+)/players", needLogin = true, type = PUT)
	public void updateProjectPlayer(User user, Matcher matcher, PlayerPermissionDto permissionDto){
		int projectId = Integer.parseInt(matcher.group(1));
		if(!ProjectController.canAdminProject(user, projectId)) throw new HttpError(403);
		if(Permission.getAccess("player", permissionDto.playerId(), "Project", projectId) < 0) throw new HttpError(400);
		int access = 0;
		if(permissionDto.add()) access+=Permission.PERM_ADD;
		if(permissionDto.update()) access+=Permission.PERM_UPDATE;
		if(permissionDto.delete()) access+=Permission.PERM_DELETE;
		if(permissionDto.admin()) access+=Permission.PERM_ADMIN;
		Permission.updateAccess("Project", projectId, "player", permissionDto.playerId(), access);
	}
	
	@Route(path = "/projects/(\\d+)/players/(\\d+)", needLogin = true, type = DELETE)
	public void deleteProjectPlayer(User user, Matcher matcher){
		int projectId = Integer.parseInt(matcher.group(1));
		if(!ProjectController.canAdminProject(user, projectId)) throw new HttpError(403);
		int playerId = Integer.parseInt(matcher.group(2));
		if(Permission.getAccess("player", playerId, "Project", projectId) < 0) throw new HttpError(400);
		Permission.revokeAccess("Project", projectId, "player", playerId);
	}
	
	@Route(path = "/tasks/(\\d+)/players", needLogin = true)
	public List<PlayerPermission> getTaskPlayers(User user, Matcher matcher){
		int taskId = Integer.parseInt(matcher.group(1));
		if(!TaskController.canAdminTask(user, taskId)) throw new HttpError(403);
		return Permission.getEntityPermissions("player", "task", taskId).stream().map(perm -> {
			return new PlayerPermission(
					perm.entityId(),
					getUsername(perm.roleId()),
					true,
					canAccess(perm.accessLevel(), Permission.PERM_ADD),
					canAccess(perm.accessLevel(), Permission.PERM_UPDATE),
					canAccess(perm.accessLevel(), Permission.PERM_DELETE),
					canAccess(perm.accessLevel(), Permission.PERM_ADMIN)
			);
		}
		).toList();
	}
	
	@Route(path = "/tasks/(\\d+)/players/(\\d+)", needLogin = true)
	public PlayerPermission getTaskPlayer(User user, Matcher matcher){
		int taskId = Integer.parseInt(matcher.group(1));
		if(!TaskController.canAdminTask(user, taskId)) throw new HttpError(403);
		int playerId = Integer.parseInt(matcher.group(2));
		int access = Permission.getAccess("player", playerId, "Task", taskId);
		if(access < 0) throw new HttpError(404);
		return new PlayerPermission(
				playerId,
				getUsername(playerId),
				true,
				canAccess(access, Permission.PERM_ADD),
				canAccess(access, Permission.PERM_UPDATE),
				canAccess(access, Permission.PERM_DELETE),
				canAccess(access, Permission.PERM_ADMIN)
			);
	}
	
	@Route(path = "/tasks/(\\d+)/players", needLogin = true, type = POST)
	public void addTaskPlayer(User user, Matcher matcher, PlayerPermissionDto permissionDto){
		int taskId = Integer.parseInt(matcher.group(1));
		if(!TaskController.canAdminTask(user, taskId)) throw new HttpError(403);
		if(Permission.getAccess("player", permissionDto.playerId(), "Task", taskId) >= 0) throw new HttpError(400);
		int access = 0;
		if(permissionDto.add()) access+=Permission.PERM_ADD;
		if(permissionDto.update()) access+=Permission.PERM_UPDATE;
		if(permissionDto.delete()) access+=Permission.PERM_DELETE;
		if(permissionDto.admin()) access+=Permission.PERM_ADMIN;
		Permission.giveAccess("Task", taskId, "player", permissionDto.playerId(), access);
	}
	
	@Route(path = "/tasks/(\\d+)/players", needLogin = true, type = PUT)
	public void updateTaskPlayer(User user, Matcher matcher, PlayerPermissionDto permissionDto){
		int taskId = Integer.parseInt(matcher.group(1));
		if(!TaskController.canAdminTask(user, taskId)) throw new HttpError(403);
		if(Permission.getAccess("player", permissionDto.playerId(), "Task", taskId) < 0) throw new HttpError(400);
		int access = 0;
		if(permissionDto.add()) access+=Permission.PERM_ADD;
		if(permissionDto.update()) access+=Permission.PERM_UPDATE;
		if(permissionDto.delete()) access+=Permission.PERM_DELETE;
		if(permissionDto.admin()) access+=Permission.PERM_ADMIN;
		Permission.updateAccess("Task", taskId, "player", permissionDto.playerId(), access);
	}
	
	@Route(path = "/tasks/(\\d+)/players/(\\d+)", needLogin = true, type = DELETE)
	public void deleteTaskPlayer(User user, Matcher matcher){
		int taskId = Integer.parseInt(matcher.group(1));
		if(!TaskController.canAdminTask(user, taskId)) throw new HttpError(403);
		int playerId = Integer.parseInt(matcher.group(2));
		if(Permission.getAccess("player", playerId, "Task", taskId) < 0) throw new HttpError(400);
		Permission.revokeAccess("Task", taskId, "player", playerId);
	}
	
	private String getUsername(int playerId){
		return TreasureCache.<Player>selectAll().filter(p->p.getId() == playerId).get().getUsername();
	}
}
