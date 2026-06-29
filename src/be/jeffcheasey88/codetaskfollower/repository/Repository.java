package be.jeffcheasey88.codetaskfollower.repository;

import java.util.List;

public interface Repository<T, K>{
	
	List<T> findAll();

	T findById(K id);
	
	K parseKey(String value);
	
}
