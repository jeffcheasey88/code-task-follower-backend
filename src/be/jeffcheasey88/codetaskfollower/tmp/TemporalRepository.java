package be.jeffcheasey88.codetaskfollower.tmp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dev.peerat.mapping.CursedTreasureException;
import dev.peerat.mapping.Ship;
import dev.peerat.mapping.ShipwreckException;
import dev.peerat.mapping.providers.mysql.MySQLCompass;
import be.jeffcheasey88.codetaskfollower.model.State;
import be.jeffcheasey88.codetaskfollower.model.Tag;

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
	
	public List<Tag> selectTags(int taskId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("SELECT t.* FROM tags t JOIN TaskTag tt ON tt.tagId = t.id JOIN tasks task ON task.id = tt.taskId WHERE task.id = ?");
			p.setInt(1, taskId);
			ResultSet r = p.executeQuery();
			List<Tag> l = new ArrayList<>();
			while(r.next()) l.add(trickTag(r.getInt("t.id"), r.getString("t.name"), r.getString("t.color")));
			return l;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to get the treasure from the treasure's cache", e);
		}

	}
	
	public List<State> selectStates(int projectId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("SELECT s.* FROM states s JOIN ProjectStates ps ON ps.stateId = s.id JOIN projects p ON p.id = ps.projectId WHERE p.id = ?");
			p.setInt(1, projectId);
			ResultSet r = p.executeQuery();
			List<State> l = new ArrayList<>();
			while(r.next()) l.add(trickState(r.getInt("s.id"), r.getString("s.name"), r.getString("s.color")));
			return l;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to get the treasure from the treasure's cache", e);
		}
	}
	
	private Tag trickTag(int id, String name, String color){
		interface NewTag{
			Tag create(int id, String name, String color);
		}
		NewTag creator = Tag::new;
		return creator.create(id, name, color);
	}
	
	private State trickState(int id, String name, String color){
		interface NewTag{
			State create(int id, String name, String color);
		}
		NewTag creator = State::new;
		return creator.create(id, name, color);
	}
}
