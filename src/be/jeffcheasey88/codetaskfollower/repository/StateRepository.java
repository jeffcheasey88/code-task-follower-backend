package be.jeffcheasey88.codetaskfollower.repository;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.model.State;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.mapping.TreasureCache;

@Injection
public class StateRepository implements Repository<State, Integer> {
	@Override
	public List<State> findAll() {
		return TreasureCache.<State>selectAll().toList();
	}

	@Override
	public State findById(Integer id) {
		return TreasureCache.<State>selectAll().filter(tag -> tag.getId() == id).get();
	}

	@Override
	public Integer parseKey(String value) {
		return Integer.parseInt(value);
	}
}
