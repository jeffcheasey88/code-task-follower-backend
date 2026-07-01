package be.jeffcheasey88.codetaskfollower.repository;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.model.Project;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.mapping.TreasureCache;

@Injection
public class ProjectRepository extends IntKeyRepository<Project> {
	@Override
	public List<Project> findAll(){
		return TreasureCache.<Project>selectAll().toList();
	}
	
    @Override
	public Project findById(Integer id){
		return TreasureCache.<Project>selectAll().filter(project -> project.getId() == id).get();
	}
}
