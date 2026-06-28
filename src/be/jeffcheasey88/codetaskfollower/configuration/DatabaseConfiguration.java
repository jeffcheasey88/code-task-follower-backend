package be.jeffcheasey88.codetaskfollower.configuration;

import be.jeffcheasey88.codetaskfollower.model.Project;
import be.jeffcheasey88.codetaskfollower.model.Task;
import dev.peerat.mapping.providers.mysql.MySQLMap;

public class DatabaseConfiguration extends MySQLMap{
	
	public DatabaseConfiguration(){
		link(javaClazz->javaClazz.toLowerCase()+"s");
		
		list(Project.class, "tasks", "id", "TaskProject", "projectId", "taskId");
		list(Project.class, "branches", "id", "ProjectBranch", "projectId", "branchId");
		list(Project.class, "states", "id", "ProjectStates", "projectId", "stateId");
		
		list(Task.class, "commits", "id", "CommitTask", "taskId", "commitId");
		list(Task.class, "branches", "id", "TaskBranch", "taskId", "branchId");
		list(Task.class, "tags", "id", "TaskTag", "taskId", "tagId");
		list(Task.class, "codes", "id", "TaskCode", "taskId", "codeId");
	}

}
