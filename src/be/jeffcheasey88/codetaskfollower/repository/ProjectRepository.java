package be.jeffcheasey88.codetaskfollower.repository;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.exceptions.NotFoundException;
import be.jeffcheasey88.codetaskfollower.model.Project;
import dev.peerat.mapping.TreasureCache;

public class ProjectRepository {
	
	
	public static List<Project> getAll() {
		return TreasureCache.<Project>selectAll().toList();
	}
	
	public static Project get(int id) throws NotFoundException {
		return TreasureCache.<Project>selectAll().filter(project -> project.getId() == id).get();
	}
	
	public static void delete(int id) throws NotFoundException {
		TreasureCache.delete(get(id));
	}
}
