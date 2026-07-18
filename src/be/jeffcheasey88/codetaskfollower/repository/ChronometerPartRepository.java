package be.jeffcheasey88.codetaskfollower.repository;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.model.Chronometer;
import be.jeffcheasey88.codetaskfollower.model.ChronometerPart;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.mapping.TreasureCache;

@Injection
public class ChronometerPartRepository extends IntKeyRepository<ChronometerPart> {
	
    @Override
	public List<ChronometerPart> findAll(){
		return TreasureCache.<ChronometerPart>selectAll().toList();
	}
	
    @Override
	public ChronometerPart findById(Integer id){
		return TreasureCache.<ChronometerPart>selectAll().filter(chrono -> chrono.getId() == id).get();
	}

}
