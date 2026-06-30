package be.jeffcheasey88.codetaskfollower.repository;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.model.Tag;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.mapping.TreasureCache;

@Injection
public class TagRepository extends IntKeyRepository<Tag>{

	@Override
	public List<Tag> findAll(){
		return TreasureCache.<Tag>selectAll().toList();
	}

	@Override
	public Tag findById(Integer id){
		return TreasureCache.<Tag>selectAll().filter(tag -> tag.getId() == id).get();
	}

}
