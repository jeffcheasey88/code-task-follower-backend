package be.jeffcheasey88.codetaskfollower.mapper;

import java.util.List;

public abstract class Mapper<D, L, E> {
	public abstract D toDto(E entity);
	public abstract L toLightDto(E entity);
	
	public abstract void fullCopyDtoToEntity(D dto, E entity);
	public abstract void safeCopyDtoToEntity(D dto, E entity);
	
	public List<L> toDto(List<E> entities) {
		return entities.stream().map(e -> toLightDto(e)).toList();
	}
}
