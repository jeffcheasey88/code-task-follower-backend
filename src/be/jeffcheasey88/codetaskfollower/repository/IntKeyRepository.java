package be.jeffcheasey88.codetaskfollower.repository;

public abstract class IntKeyRepository<T> implements Repository<T, Integer>{

	@Override
	public Integer parseKey(String value){
		return Integer.parseInt(value);
	}
}
