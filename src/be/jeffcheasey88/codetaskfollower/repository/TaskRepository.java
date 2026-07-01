package be.jeffcheasey88.codetaskfollower.repository;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.model.Task;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.mapping.TreasureCache;

@Injection
public class TaskRepository extends IntKeyRepository<Task> {
    @Override
	public List<Task> findAll(){
		return TreasureCache.<Task>selectAll().toList();
	}
	
    @Override
	public Task findById(Integer id){
		return TreasureCache.<Task>selectAll().filter(task -> task.getId() == id).get();
	}

}
