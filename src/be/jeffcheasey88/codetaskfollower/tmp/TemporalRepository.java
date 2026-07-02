package be.jeffcheasey88.codetaskfollower.tmp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import dev.peerat.mapping.CursedTreasureException;
import dev.peerat.mapping.Ship;
import dev.peerat.mapping.ShipwreckException;
import dev.peerat.mapping.providers.mysql.MySQLCompass;

public class TemporalRepository{
	
	public static TemporalRepository INSTANCE = new TemporalRepository();
	private Connection con;
	private MySQLCompass compass;
	
	public void connector(Ship ship){
		this.compass = ((MySQLCompass)ship.getCompass());
	}
	private void ensureConnection(){
		try{
			if(con == null || !(con.isValid(5))) this.con = DriverManager.getConnection("jdbc:mysql://" + compass.getHost() + ":" + compass.getPort() + "/" + compass.getDatabase(), compass.getUser(), compass.getPassword());
		}catch(Exception e){
			throw new ShipwreckException("The ship couldn't set sail", e);
		}
	}
	
	//ProjectStates (projectId, stateId)
	public void insertProjectStates(int projectId, int stateId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("INSERT INTO ProjectStates (projectId,stateId) VALUES (?,?)");
			p.setInt(1, projectId);
			p.setInt(2, stateId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	public void removeProjectStates(int projectId, int stateId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("DELETE FROM ProjectStates WHERE projectId = ? AND stateId = ?");
			p.setInt(1, projectId);
			p.setInt(2, stateId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	
	//tasks (stateId)
	public void updateTaskState(int taskId, int stateId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("UPDATE tasks SET stateId = ? WHERE id = ?");
			p.setInt(1, stateId);
			p.setInt(2, taskId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}
		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	//TaskProject (projectId, taskId)
	public void insertTaskProject(int projectId, int taskId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("INSERT INTO TaskProject (projectId,taskId) VALUES (?,?)");
			p.setInt(1, projectId);
			p.setInt(2, taskId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	public void removeTaskProject(int projectId, int taskId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("DELETE FROM TaskProject WHERE projectId = ? AND taskId = ?");
			p.setInt(1, projectId);
			p.setInt(2, taskId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	//CommitTask (commitId, taskId)
	public void insertCommitTask(int commitId, int taskId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("INSERT INTO CommitTask (commitId,taskId) VALUES (?,?)");
			p.setInt(1, commitId);
			p.setInt(2, taskId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	public void removeCommitTask(int commitId, int taskId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("DELETE FROM CommitTask WHERE commitId = ? AND taskId = ?");
			p.setInt(1, commitId);
			p.setInt(2, taskId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	//chronometers (taskId)
	public void updateChronometerTask(int chronometerId, int taskId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("UPDATE chronometers SET taskId = ? WHERE id = ?");
			p.setInt(1, taskId);
			p.setInt(2, chronometerId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}
		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	//chronometerparts (chronometerId)
	public void updateChronometerPart(int chronometerPartId, int taskchronometerIdId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("UPDATE chronometerparts SET chronometerId = ? WHERE id = ?");
			p.setInt(1, taskchronometerIdId);
			p.setInt(2, chronometerPartId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}
		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	//TaskBranch (taskId, branchId)
	public void insertTaskBranch(int taskId, int branchId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("INSERT INTO TaskBranch (taskId,branchId) VALUES (?,?)");
			p.setInt(1, taskId);
			p.setInt(2, branchId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	public void removeTaskBranch(int taskId, int branchId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("DELETE FROM TaskBranch WHERE taskId = ? AND branchId = ?");
			p.setInt(1, taskId);
			p.setInt(2, branchId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	//ProjectBranch (projectId, branchId)
	public void insertProjectBranch(int projectId, int branchId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("INSERT INTO ProjectBranch (projectId,branchId) VALUES (?,?)");
			p.setInt(1, projectId);
			p.setInt(2, branchId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	public void removeProjectBranch(int projectId, int branchId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("DELETE FROM ProjectBranch WHERE projectId = ? AND branchId = ?");
			p.setInt(1, projectId);
			p.setInt(2, branchId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	//TaskTag (taskId, tagId)
	public void insertTaskTag(int taskId, int tagId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("INSERT INTO TaskTag (taskId,tagId) VALUES (?,?)");
			p.setInt(1, taskId);
			p.setInt(2, tagId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	public void removeTaskTag(int taskId, int tagId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("DELETE FROM TaskTag WHERE taskId = ? AND tagId = ?");
			p.setInt(1, taskId);
			p.setInt(2, tagId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	//codes (branch)
	public void updateCodeBranch(int codeId, int branchId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("UPDATE codes SET branch = ? WHERE id = ?");
			p.setInt(1, branchId);
			p.setInt(2, codeId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}
		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	//TaskCode (taskId, codeId)
	public void insertTaskCode(int taskId, int codeId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("INSERT INTO TaskCode (taskId,codeId) VALUES (?,?)");
			p.setInt(1, taskId);
			p.setInt(2, codeId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	public void removeTaskCode(int taskId, int codeId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("DELETE FROM TaskCode WHERE taskId = ? AND codeId = ?");
			p.setInt(1, taskId);
			p.setInt(2, codeId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}

}
