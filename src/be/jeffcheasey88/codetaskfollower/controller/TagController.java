package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.*;

import java.util.List;

import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Key;
import be.jeffcheasey88.codetaskfollower.model.Tag;
import be.jeffcheasey88.codetaskfollower.model.dto.TagDto;
import be.jeffcheasey88.codetaskfollower.repository.TagRepository;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class TagController{
	
	@Injection private TagRepository tagRepository;
	
	@Route(path = "/tags", type = GET)
	public List<Tag> getTags(){
		return tagRepository.findAll();
	}

	@Route(path = "/tags", type = POST)
	public void createTag(TagDto tagDto){
		new Tag(0, tagDto.getName(), tagDto.getColor());
	}
	
	@Route(path = "/tags/(\\d+)", type = PUT)
	public void editTag(TagDto tagDto, @Key Tag tag){
		tag.setName(tagDto.getName());
		tag.setColor(tagDto.getColor());
	}
	
	@Route(path = "/tags/(\\d+)", type = DELETE)
	public void deleteTag(@Key Tag tag){
		TreasureCache.delete(tag);
	}
}
