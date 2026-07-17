package be.jeffcheasey88.codetaskfollower.tmp;

import java.sql.SQLException;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository.SqlParam;

public class Permission{
	
	public static int PERM_READ = 0;
	public static int PERM_ADD = 1;
	public static int PERM_UPDATE = 2;
	public static int PERM_DELETE = 4;
	public static int PERM_ADMIN = 8;
	
	public static boolean canAccess(int access, int perm){
		int filter = access&perm;
		return filter != 0;
	}

	public static void giveAccess(String entityType, int entityId, String roleType, int roleId, int accessLevel){
		TemporalRepository.INSTANCE.externalUpdateRequest(
				"INSERT INTO "+entityType+"Access (roleType,roleId,"+entityType.toLowerCase()+"Id, accessLevel) VALUES (?,?,?,?)",
				new SqlParam("String",roleType),
				new SqlParam("int",roleId),
				new SqlParam("int",entityId),
				new SqlParam("int",accessLevel)
				);
	}
	
	public static void updateAccess(String entityType, int entityId, String roleType, int roleId, int accessLevel){
		revokeAccess(entityType, entityId, roleType, roleId);
		giveAccess(entityType, entityId, roleType, roleId, accessLevel);
	}
	
	public static void revokeAccess(String entityType, int entityId, String roleType, int roleId){
		TemporalRepository.INSTANCE.externalUpdateRequest(
				"DELETE FROM "+entityType+"Access WHERE roleType = ? AND roleId = ? AND "+entityType.toLowerCase()+"Id = ?)",
				new SqlParam("String",roleType),
				new SqlParam("int",roleId),
				new SqlParam("int",entityId)
				);
	}
	
	public static boolean canAccessProject(int playerId, int projectId, Predicate<Integer> validator){
		List<Integer> results = TemporalRepository.INSTANCE.externalSelectRequest(
				"SELECT accessLevel FROM ProjectAccessView WHERE playerId = ? AND projectId = ?",
				(r) -> {
					try {
						return r.getInt("accessLevel");
					} catch (SQLException e){
						throw new RuntimeException("??");
					}
				},
				new SqlParam("int",playerId),
				new SqlParam("int",projectId));
		return ((!results.isEmpty()) && validator.test(results.get(0)));
	}
	
	public static boolean canAccessTask(int playerId, int taskId, Predicate<Integer> taskValidator, BiPredicate<Integer, Boolean	> projectValidator){
		List<TreasureAccess> results = TemporalRepository.INSTANCE.externalSelectRequest(
				"SELECT accessLevel, entityLevel FROM TaskAccessView WHERE playerId = ? AND taskId = ?",
				(r) -> {
					try {
						return new TreasureAccess(r.getInt("accessLevel"), r.getString("entityLevel"));
					} catch (SQLException e){
						throw new RuntimeException("??");
					}
				},
				new SqlParam("int",playerId),
				new SqlParam("int",taskId));
		if(results.isEmpty()) return false;
		
		boolean hasTaskPermission = false;
		for(TreasureAccess access : results){
			if(access.entityLevel.equals("task")){
				if(taskValidator.test(access.accessLevel)) return true;
				hasTaskPermission = true;
			}
		}
		
		for(TreasureAccess access : results){
			if(access.entityLevel.equals("project")){
				if(projectValidator.test(access.accessLevel, hasTaskPermission)) return true;
			}
		}
		
		return false;
	}
	
	private record TreasureAccess(int accessLevel, String entityLevel){}
}
