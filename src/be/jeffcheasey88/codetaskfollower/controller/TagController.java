package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.dto.ModelUpdateDto;
import be.jeffcheasey88.codetaskfollower.dto.TagDto;
import be.jeffcheasey88.codetaskfollower.mapper.TagMapper;
import be.jeffcheasey88.codetaskfollower.model.Tag;
import be.jeffcheasey88.codetaskfollower.repository.TagRepository;
import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository;
import dev.peerat.framework.Locker;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class TagController {
	
	@Injection private TagRepository tagRepository;
	@Injection private TagMapper tagMapper;
	@Injection("modelUpdater") private Locker<ModelUpdateDto> modelLocker;
	
	@Route(path = "/tags", needLogin = true)
	public List<TagDto> getTags(){
		return tagMapper.toDto(tagRepository.findAll());
	}

	@Route(path = "/tags", type = POST, needLogin = true)
	public int createTag(TagDto tagDto) {
		return new Tag(0, tagDto.getName(), tagDto.getColor()).getId();
	}
	
	@Route(path = "/tags/(\\d+)", type = PUT, needLogin = true)
	public void editTag(TagDto tagDto, @Argument Tag tag){
		tagMapper.fullCopyDtoToModel(tagDto, tag);
		TemporalRepository.INSTANCE.updateTag(tag);
	}
	
	@Route(path = "/tags/(\\d+)", type = DELETE, needLogin = true)
	public void deleteTag(@Argument Tag tag) {
		TreasureCache.delete(tag);
	}
	
}
