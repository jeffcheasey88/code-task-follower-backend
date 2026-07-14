package be.jeffcheasey88.codetaskfollower.tmp;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository.SqlParam;

public class Permission{

	private static void giveAccess(String entityType, int entityId, String roleType, int roleId, int accessLevel){
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
	
	public static boolean canAccess(int playerId, String entityType, int entityId, Predicate<Integer> validator){
		List<Integer> results = TemporalRepository.INSTANCE.externalSelectRequest(
				"SELECT accessLevel FROM "+entityType+"Access WHERE roleType = 'player' AND roleId = ? AND "+entityType.toLowerCase()+"Id = ?",
				(r) -> {
					try {
						return r.getInt("accessLevel");
					} catch (SQLException e){
						throw new RuntimeException("??");
					}
				},
				new SqlParam("int",playerId),
				new SqlParam("int",entityId));
		if((!results.isEmpty()) && validator.test(results.get(0))) return true; 
		
		results = TemporalRepository.INSTANCE.externalSelectRequest(
				"SELECT a.accessLevel FROM "+entityType+"Access a JOIN PlayerGroup pg ON pg.groupId = a.roleId JOIN players p ON p.id = pg.playerId WHERE a.roleType = 'group' AND p.id = ? AND a."+entityType.toLowerCase()+"Id = ?",
				(r) -> {
					try {
						return r.getInt("accessLevel");
					} catch (SQLException e){
						throw new RuntimeException("??");
					}
				},
				new SqlParam("int",playerId),
				new SqlParam("int",entityId));
		if((!results.isEmpty()) && validator.test(results.get(0))) return true;
		
		return false;
	}
	
}
