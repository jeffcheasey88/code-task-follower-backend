package be.jeffcheasey88.codetaskfollower.tmp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import be.jeffcheasey88.codetaskfollower.model.Branch;
import be.jeffcheasey88.codetaskfollower.model.ChronometerPart;
import be.jeffcheasey88.codetaskfollower.model.Code;
import be.jeffcheasey88.codetaskfollower.model.Commit;
import be.jeffcheasey88.codetaskfollower.model.Project;
import be.jeffcheasey88.codetaskfollower.model.State;
import be.jeffcheasey88.codetaskfollower.model.Tag;
import be.jeffcheasey88.codetaskfollower.model.Task;
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
	public static void insertProjectStates(Project project, State state){
		insert(
				"ProjectStates",
				List.of("projectId", "stateId"),
				param(project.getId()),
				param(state.getId())
		);
		trickAppend(project::getStates, project::setStates, state);
	}

	public static void removeProjectStates(Project project, State state){
		delete(
				"ProjectStates",
				List.of("projectId", "stateId"),
				param(project.getId()),
				param(state.getId())
		);
		trickRemove(project::getStates, project::setStates, s -> s.getId() != state.getId());
	}
	
	
	//tasks (stateId)
	public static void updateTaskState(int taskId, int stateId){
		update(
				"tasks",
				List.of("stateId"),
				List.of("id"),
				param(stateId),
				param(taskId)
		);
	}
	
	//TaskProject (projectId, taskId)
	public static void insertTaskProject(int projectId, int taskId){
		insert(
				"TaskProject",
				List.of("projectId", "taskId"),
				param(projectId),
				param(taskId)
		);
	}
	
	public static void removeTaskProject(int projectId, int taskId){
		delete(
				"TaskProject",
				List.of("projectId", "taskId"),
				param(projectId),
				param(taskId)
		);
	}

	public static void removeTaskAnyProject(int taskId){
		delete(
				"TaskProject",
				List.of("taskId"),
				param(taskId)
		);
	}

	public static void removeTaskAnyTag(int taskId){
		delete(
				"TaskTag",
				List.of("taskId"),
				param(taskId)
		);
	}
	
	//CommitTask (commitId, taskId)
	public static void insertCommitTask(int commitId, int taskId){
		insert(
				"CommitTask",
				List.of("commitId", "taskId"),
				param(commitId),
				param(taskId)
		);
	}
	
	public static void removeCommitTask(int commitId, int taskId){
		delete(
				"CommitTask",
				List.of("commitId","taskId"),
				param(commitId),
				param(taskId)
		);
	}
	
	//chronometers (taskId)
	public static void updateChronometerTask(int chronometerId, int taskId){
		update(
				"chronometers",
				List.of("taskId"),
				List.of("id"),
				param(taskId),
				param(chronometerId)
		);
	}
	
	//chronometerparts (chronometerId)
	public static void updateChronometerPart(int chronometerPartId, int chronometerId){
		update(
				"chronometerparts",
				List.of("chronometerId"),
				List.of("id"),
				param(chronometerId),
				param(chronometerPartId)
		);
	}
	
	//TaskBranch (taskId, branchId)
	public static void insertTaskBranch(int taskId, int branchId){
		insert(
				"TaskBranch",
				List.of("taskId", "branchId"),
				param(taskId),
				param(branchId)
		);
	}
	
	public static void removeTaskBranch(int taskId, int branchId){
		delete(
				"TaskBranch",
				List.of("taskId","branchId"),
				param(taskId),
				param(branchId)
		);
	}
	
	//ProjectBranch (projectId, branchId)
	public static void insertProjectBranch(int projectId, int branchId){
		insert(
				"ProjectBranch",
				List.of("projectId", "branchId"),
				param(projectId),
				param(branchId)
		);
	}
	
	public static void removeProjectBranch(int projectId, int branchId){
		delete(
				"ProjectBranch",
				List.of("projectId","branchId"),
				param(projectId),
				param(branchId)
		);
	}
	
	//TaskTag (taskId, tagId)
	public static void insertTaskTag(Task task, Tag tag){
		insert(
				"TaskTag",
				List.of("taskId", "tagId"),
				param(task.getId()),
				param(tag.getId())
		);
		trickAppend(task::getTags, task::setTags, tag);
	}
	
	public static void removeTaskTag(Task task, Tag tag){
		delete(
				"TaskTag",
				List.of("taskId","tagId"),
				param(task.getId()),
				param(tag.getId())
		);
		trickRemove(task::getTags, task::setTags, t -> t.getId() != tag.getId());
	}
	
	//codes (branch)
	public static void updateCodeBranch(int codeId, int branchId){
		update(
				"codes",
				List.of("branch"),
				List.of("id"),
				param(branchId),
				param(codeId)
		);
	}
	
	//TaskCode (taskId, codeId)
	public static void insertTaskCode(int taskId, int codeId){
		insert(
				"TaskCode",
				List.of("taskId", "codeId"),
				param(taskId),
				param(codeId)
		);
	}
	
	public static void removeTaskCode(int taskId, int codeId){
		delete(
				"TaskCode",
				List.of("taskId","codeId"),
				param(taskId),
				param(codeId)
		);
	}
	
	public static List<Tag> selectTags(int taskId){
		return listSelect(
				"SELECT t.* FROM tags t JOIN TaskTag tt ON tt.tagId = t.id JOIN tasks task ON task.id = tt.taskId WHERE task.id = ?",
				r -> trickTag(r.getInt("t.id"), r.getString("t.name"), r.getString("t.color")),
				param(taskId)
				);
	}
	
	public static List<State> selectStates(int projectId){
		return listSelect(
				"SELECT s.* FROM states s JOIN ProjectStates ps ON ps.stateId = s.id JOIN projects p ON p.id = ps.projectId WHERE p.id = ?",
				r -> trickState(r.getInt("s.id"), r.getString("s.name"), r.getString("s.color")),
				param(projectId)
				);
	}
	
	public static List<Task> selectTasks(int projectId){
		return listSelect(
				"SELECT t.* FROM tasks t JOIN TaskProject tp ON tp.taskId = t.id WHERE tp.projectId = ?",
				r -> trickTask(r.getInt("t.id"), r.getString("t.name")),
				param(projectId)
				);
	}

	public static State selectStateForTask(int taskId){
		return select(
				"SELECT s.* FROM tasks t JOIN states s ON t.stateId = s.id WHERE t.id = ?",
				r -> trickState(r.getInt("s.id"), r.getString("s.name"), r.getString("s.color")),
				param(taskId)
				);
	}
	
	//UPDATES
	public static void updateTask(Task task){
		update(
				"tasks",
				List.of("name","description"),
				List.of("id"),
				param(task.getName()),
				param(task.getDescription()),
				param(task.getId())
		);
	}
	
	public static void updateTag(Tag tag){
		update(
				"tags",
				List.of("name","color"),
				List.of("id"),
				param(tag.getName()),
				param(tag.getColor()),
				param(tag.getId())
		);
	}
	
	public static void updateState(State state){
		update(
				"states",
				List.of("name","color"),
				List.of("id"),
				param(state.getName()),
				param(state.getColor()),
				param(state.getId())
		);
	}
	
	public static void updateChronometerPart(ChronometerPart part){
		update(
				"chronometerparts",
				List.of("seconds","description"),
				List.of("id"),
				param(part.getSeconds()),
				param(part.getDescription()),
				param(part.getId())
		);
	}
	
	public static void updateProject(Project project){
		update(
				"projects",
				List.of("name","color","description"),
				List.of("id"),
				param(project.getName()),
				param(project.getColor()),
				param(project.getDescription()),
				param(project.getId())
		);
	}
	
	public static <T> List<T> listSelect(String sqlRequest, Converter<ResultSet, T> mapper, SqlParam... params){
		return INSTANCE.selectRequest(sqlRequest, mapper, params);
	}
	
	public static <T> T select(String sqlRequest, Converter<ResultSet, T> mapper, SqlParam... params){
		List<T> results = INSTANCE.selectRequest(sqlRequest, mapper, params);
		if(results == null || results.isEmpty()) return null;
		return results.get(0);
	}
	
	public static void delete(String table, List<String> fields, SqlParam... params){
		String whereFields = "";
		Iterator<String> whereIterator = fields.iterator();
		while(whereIterator.hasNext()){
			whereFields+=whereIterator.next()+" = ?";
			if(whereIterator.hasNext()) whereFields+=" AND ";
		}
		INSTANCE.updateRequest(
				"DELETE FROM "+table+" WHERE "+whereFields,
				params
				);
	}
	
	public static void insert(String table, List<String> fields, SqlParam... params){
		String updateFields = "";
		String bindingFields = "";
		Iterator<String> fieldIterator = fields.iterator();
		while(fieldIterator.hasNext()){
			updateFields+=fieldIterator.next();
			bindingFields+="?";
			if(fieldIterator.hasNext()){
				updateFields+=",";
				bindingFields+=",";
			}
		}
		INSTANCE.updateRequest(
				"INSERT INTO "+table+" ("+updateFields+") VALUES ("+bindingFields+")",
				params
				);
	}
	
	public static void update(String table, List<String> fields, List<String> idField, SqlParam... params){
		String updateFields = "";
		Iterator<String> fieldIterator = fields.iterator();
		while(fieldIterator.hasNext()){
			updateFields+=fieldIterator.next()+" = ?";
			if(fieldIterator.hasNext()) updateFields+=",";
		}
		String whereFields = "";
		Iterator<String> whereIterator = idField.iterator();
		while(whereIterator.hasNext()){
			whereFields+=whereIterator.next()+" = ?";
			if(whereIterator.hasNext()) whereFields+=" AND ";
		}
		INSTANCE.updateRequest(
				"UPDATE "+table+" SET "+updateFields+" WHERE "+whereFields,
				params
				);
	}
	
	public static SqlParam param(String value){
		return new SqlParam("String", value);
	}
	
	public static SqlParam param(int value){
		return new SqlParam("int", value);
	}
	
	public void updateRequest(String sqlRequest, SqlParam... params){
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
	
	public <T> List<T> selectRequest(String sqlRequest, Converter<ResultSet, T> mapper, SqlParam... params){
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
			while(r.next()) l.add(mapper.convert(r));
			return l;
		}catch(Exception e){
			throw new CursedTreasureException("Failed to set the treasure in the treasure's cache", e);
		}
	}
	
	public static record SqlParam(String type, Object value){}
	
	public static interface Converter<A, B>{
		
		B convert(A a) throws Exception;
		
	}
	
	private static <T> void trickAppend(Supplier<List<T>> getter, Consumer<List<T>> setter, T value){
		List<T> list = getter.get();
		if(list == null) setter.accept(list = new LinkedList<T>());
		list.add(value);
	}
	
	private static <T> void trickRemove(Supplier<List<T>> getter, Consumer<List<T>> setter, Predicate<T> filter){
		List<T> list = getter.get();
		if(list == null) return;
		setter.accept(list.stream().filter(filter).toList());
	}

	interface NewTag{
		Tag create(int id, String name, String color);
	}
	
	private static Tag trickTag(int id, String name, String color){
		NewTag creator = Tag::new;
		return creator.create(id, name, color);
	}
	
	interface NewState{
		State create(int id, String name, String color);
	}
	private static State trickState(int id, String name, String color){
		NewState creator = State::new;
		return creator.create(id, name, color);
	}

	interface NewTask{
		Task create(int id, String name, String description, int estimateSeconds, List<Tag> tags, List<Task> dependencies, List<Project> projects, List<Commit> commits, List<Branch> branches, List<Code> codes);
	}
	private static Task trickTask(int id, String name){
		NewTask creator = Task::new;
		return creator.create(id, name, null, 0, null, null, null, null, null, null);
	}
}
