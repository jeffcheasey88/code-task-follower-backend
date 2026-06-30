package be.jeffcheasey88.codetaskfollower.mapper;

import java.util.List;

public abstract class Mapper<D, L, M> {
	public abstract D toDto(M model);
	public abstract L toLightDto(M model);
	
	public abstract void fullCopyDtoToModel(D dto, M model);
	public abstract void safeCopyDtoToModel(D dto, M model);
	
	public List<L> toDto(List<M> models) {
		return models.stream().map(this::toLightDto).toList();
	}
}
