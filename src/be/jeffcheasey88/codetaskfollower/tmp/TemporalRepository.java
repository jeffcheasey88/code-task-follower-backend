package be.jeffcheasey88.codetaskfollower.tmp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import dev.peerat.mapping.CursedTreasureException;
import dev.peerat.mapping.Ship;
import dev.peerat.mapping.ShipwreckException;
import dev.peerat.mapping.providers.mysql.MySQLCompass;
import be.jeffcheasey88.codetaskfollower.model.Branch;
import be.jeffcheasey88.codetaskfollower.model.Code;
import be.jeffcheasey88.codetaskfollower.model.Commit;
import be.jeffcheasey88.codetaskfollower.model.Project;
import be.jeffcheasey88.codetaskfollower.model.State;
import be.jeffcheasey88.codetaskfollower.model.Tag;
import be.jeffcheasey88.codetaskfollower.model.Task;

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
	public void insertProjectStates(Project project, State state){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("INSERT INTO ProjectStates (projectId,stateId) VALUES (?,?)");
			p.setInt(1, project.getId());
			p.setInt(2, state.getId());
			if(project.getStates() == null) project.setStates(new LinkedList<>());
			project.getStates().add(state);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}

	public void insertProjectTasks(int projectId, int taskId){
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
	
	public void removeProjectStates(Project project, State state){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("DELETE FROM ProjectStates WHERE projectId = ? AND stateId = ?");
			p.setInt(1, project.getId());
			p.setInt(2, state.getId());
			if(project.getStates() != null){
				project.setStates(project.getStates().stream().filter(s->s.getId() != state.getId()).toList());
			}
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

	public void removeTaskAnyProject(int taskId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("DELETE FROM TaskProject WHERE taskId = ?");
			p.setInt(1, taskId);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}

	public void removeTaskAnyTag(int taskId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("DELETE FROM TaskTag WHERE taskId = ?");
			p.setInt(1, taskId);
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
	public void insertTaskTag(Task task, Tag tag){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("INSERT INTO TaskTag (taskId,tagId) VALUES (?,?)");
			p.setInt(1, task.getId());
			p.setInt(2, tag.getId());
			if(task.getTags() == null) task.setTags(new LinkedList<>());
			task.getTags().add(tag);
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}

		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	public void removeTaskTag(Task task, Tag tag){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("DELETE FROM TaskTag WHERE taskId = ? AND tagId = ?");
			p.setInt(1, task.getId());
			p.setInt(2, tag.getId());
			if(task.getTags() != null){
				task.setTags(task.getTags().stream().filter(t->t.getId() != tag.getId()).toList());
			}
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
	
	public List<Task> selectTasks(int projectId){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("SELECT t.* FROM tasks t JOIN TaskProject tp ON tp.taskId = t.id WHERE tp.projectId = ?");
			p.setInt(1, projectId);
			ResultSet r = p.executeQuery();
			List<Task> l = new ArrayList<>();
			while(r.next()) l.add(trickTask(r.getInt("t.id"), r.getString("t.name")));
			return l;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to get the treasure from the treasure's cache", e);
		}
	}

	public State selectStateForTask(int taskId) {
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("SELECT s.* FROM tasks t JOIN states s ON t.stateId = s.id WHERE t.id = ?");
			p.setInt(1, taskId);
			ResultSet r = p.executeQuery();
			r.next();
			return trickState(r.getInt("s.id"), r.getString("s.name"), r.getString("s.color"));
		}catch(Exception e){
			throw new CursedTreasureException("Failed to get the treasure from the treasure's cache", e);
		}
	}
	
	//UPDATES
	public void updateTask(Task task){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("UPDATE tasks SET name = ?, description = ? WHERE id = ?");
			p.setString(1, task.getName());
			p.setString(2, task.getDescription());
			p.setInt(3, task.getId());
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}
		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	public void updateTag(Tag tag){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("UPDATE tags SET name = ?, color = ? WHERE id = ?");
			p.setString(1, tag.getName());
			p.setString(2, tag.getColor());
			p.setInt(3, tag.getId());
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}
		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	public void updateState(State state){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("UPDATE states SET name = ?, color = ? WHERE id = ?");
			p.setString(1, state.getName());
			p.setString(2, state.getColor());
			p.setInt(3, state.getId());
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}
		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	public void updateProject(Project project){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement("UPDATE projects SET name = ?, color = ?, description = ? WHERE id = ?");
			p.setString(1, project.getName());
			p.setString(2, project.getColor());
			p.setString(3, project.getDescription());
			p.setInt(4, project.getId());
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}
		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	public void externalUpdateRequest(String sqlRequest, SqlParam... params){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement(sqlRequest);
			if(params != null && params.length > 0){
				for(int index = 0; index < params.length; index++){
					SqlParam param = params[index];
					if(param.type().equals("String")){
						p.setString(index+1, (String) param.value());
					}else if(param.type().equals("int")){
						p.setInt(index+1, (int) param.value());
					}
				}
			}
			if(p.executeUpdate() >= 0) return;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}
		throw new CursedTreasureException("Failed to set the treasure in the treasure's cache");
	}
	
	public <T> List<T> externalSelectRequest(String sqlRequest, Function<ResultSet, T> mapper, SqlParam... params){
		ensureConnection();
		try{
			PreparedStatement p = this.con.prepareStatement(sqlRequest);
			if(params != null && params.length > 0){
				for(int index = 0; index < params.length; index++){
					SqlParam param = params[index];
					if(param.type().equals("String")){
						p.setString(index+1, (String) param.value());
					}else if(param.type().equals("int")){
						p.setInt(index+1, (int) param.value());
					}
				}
			}
			ResultSet r = p.executeQuery();
			List<T> l = new ArrayList<>();
			while(r.next()) l.add(mapper.apply(r));
			return l;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}
	}
	
	public static record SqlParam(String type, Object value){}

	interface NewTag{
		Tag create(int id, String name, String color);
	}
	
	private Tag trickTag(int id, String name, String color){
		NewTag creator = Tag::new;
		return creator.create(id, name, color);
	}
	
	interface NewState{
		State create(int id, String name, String color);
	}
	private State trickState(int id, String name, String color){
		NewState creator = State::new;
		return creator.create(id, name, color);
	}

	interface NewTask{
		Task create(int id, String name, String description, List<Tag> tags, List<Task> dependencies, List<Project> projects, List<Commit> commits, List<Branch> branches, List<Code> codes);
	}
	private Task trickTask(int id, String name){
		NewTask creator = Task::new;
		return creator.create(id, name, null, null, null, null, null, null, null);
	}
}
