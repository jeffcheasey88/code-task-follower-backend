package be.jeffcheasey88.codetaskfollower.mapper;

import be.jeffcheasey88.codetaskfollower.model.Tag;
import be.jeffcheasey88.codetaskfollower.model.dto.TagDto;
import dev.peerat.framework.dependency.Injection;

@Injection
public class TagMapper extends Mapper<TagDto, TagDto, Tag> {
	@Override
	public TagDto toDto(Tag entity) {
		return new TagDto(entity.getId(), entity.getName(), entity.getColor());
	}

	@Override
	public TagDto toLightDto(Tag entity) {
		return toDto(entity);
	}

	@Override
	public void fullCopyDtoToEntity(TagDto dto, Tag entity) {
		entity.setName(dto.getName());
		entity.setColor(dto.getColor());
	}

	@Override
	public void safeCopyDtoToEntity(TagDto dto, Tag entity) {
		if (dto.getName() != null) entity.setName(dto.getName());
		if (dto.getColor() != null) entity.setColor(dto.getColor());
	}
}
