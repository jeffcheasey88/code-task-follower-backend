package be.jeffcheasey88.codetaskfollower.configuration;

import be.jeffcheasey88.codetaskfollower.model.Commit;
import be.jeffcheasey88.codetaskfollower.model.Project;
import be.jeffcheasey88.codetaskfollower.model.Task;
import dev.peerat.mapping.providers.mysql.MySQLMap;

public class DatabaseConfiguration extends MySQLMap{
	
	public DatabaseConfiguration(){
		link(javaClazz->javaClazz.toLowerCase()+"s");
		
		list(Project.class, "tasks", "id");
		list(Project.class, "branches", "id");
		list(Project.class, "states", "id");
		
		list(Task.class, "projects", "id");
		list(Task.class, "commits", "id");
		list(Task.class, "branches", "id");
		list(Task.class, "tags", "id");
		list(Task.class, "codes", "id");
		
		list(Commit.class, "tasks", "id");
	}

}
