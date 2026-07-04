package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.GET;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.dto.TagDto;
import be.jeffcheasey88.codetaskfollower.mapper.TagMapper;
import be.jeffcheasey88.codetaskfollower.model.Tag;
import be.jeffcheasey88.codetaskfollower.repository.TagRepository;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class TagController {
	
	@Injection private TagRepository tagRepository;
	@Injection private TagMapper tagMapper;
	
	@Route(path = "/tags", type = GET)
	public List<TagDto> getTags(){
		return tagMapper.toDto(tagRepository.findAll());
	}

	@Route(path = "/tags", type = POST)
	public void createTag(TagDto tagDto) {
		new Tag(0, tagDto.getName(), tagDto.getColor());
	}
	
	@Route(path = "/tags/(\\d+)", type = PUT)
	public void editTag(TagDto tagDto, @Argument Tag tag) {
		tagMapper.fullCopyDtoToModel(tagDto, tag);
	}
	
	@Route(path = "/tags/(\\d+)", type = DELETE)
	public void deleteTag(@Argument Tag tag) {
		TreasureCache.delete(tag);
	}
}
