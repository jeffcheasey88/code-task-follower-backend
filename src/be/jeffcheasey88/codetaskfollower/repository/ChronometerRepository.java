package be.jeffcheasey88.codetaskfollower.repository;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.model.Chronometer;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.mapping.TreasureCache;

@Injection
public class ChronometerRepository extends IntKeyRepository<Chronometer> {
	
    @Override
	public List<Chronometer> findAll(){
		return TreasureCache.<Chronometer>selectAll().toList();
	}
	
    @Override
	public Chronometer findById(Integer id){
		return TreasureCache.<Chronometer>selectAll().filter(chrono -> chrono.getId() == id).get();
	}

}
