package be.jeffcheasey88.codetaskfollower.mapper;

import be.jeffcheasey88.codetaskfollower.model.Tag;
import be.jeffcheasey88.codetaskfollower.dto.TagDto;
import dev.peerat.framework.dependency.Injection;

@Injection
public class TagMapper extends Mapper<TagDto, TagDto, Tag> {
	@Override
	public TagDto toDto(Tag model) {
		return new TagDto(model.getId(), model.getName(), model.getColor());
	}

	@Override
	public TagDto toLightDto(Tag model) {
		return toDto(model);
	}

	@Override
	public void fullCopyDtoToModel(TagDto dto, Tag model) {
		model.setName(dto.getName());
		model.setColor(dto.getColor());
	}

	@Override
	public void safeCopyDtoToModel(TagDto dto, Tag model) {
		if (dto.getName() != null) model.setName(dto.getName());
		if (dto.getColor() != null) model.setColor(dto.getColor());
	}
}
