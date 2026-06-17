package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.*;

import java.util.List;
import java.util.regex.Matcher;

import be.jeffcheasey88.codetaskfollower.model.Tag;
import be.jeffcheasey88.codetaskfollower.model.dto.TagDto;
import dev.peerat.framework.routes.Route;
import dev.peerat.mapping.TreasureCache;

public class TagController{
	
	@Route(path = "/tags", type = GET)
	public List<Tag> getTags(){
		return Tag.getTags();
	}

	@Route(path = "/tags", type = POST)
	public void createTag(TagDto tagDto){
		new Tag(0, tagDto.getName(), tagDto.getColor());
	}
	
	@Route(path = "/tags/(\\d+)", type = PUT)
	public void editTag(Matcher matcher, TagDto tagDto){
		Tag tag = Tag.getTag(Integer.parseInt(matcher.group(1)));
		tag.setName(tagDto.getName());
		tag.setColor(tagDto.getColor());
	}
	
	@Route(path = "/tags/(\\d+)", type = DELETE)
	public void deleteTag(Matcher matcher){
		TreasureCache.delete(Tag.getTag(Integer.parseInt(matcher.group(1))));
	}
}
