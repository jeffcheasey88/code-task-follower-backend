package be.jeffcheasey88.codetaskfollower.mapper;

import be.jeffcheasey88.codetaskfollower.dto.ChronometerPartDto;
import be.jeffcheasey88.codetaskfollower.model.ChronometerPart;
import dev.peerat.framework.dependency.Injection;

@Injection
public class ChronometerPartMapper extends Mapper<ChronometerPartDto, ChronometerPartDto, ChronometerPart>{
	
	@Override
	public ChronometerPartDto toDto(ChronometerPart model) {
		return new ChronometerPartDto(model.getId(), model.getSeconds(), model.getDescription());
	}

	@Override
	public ChronometerPartDto toLightDto(ChronometerPart model) {
		return toDto(model);
	}

	@Override
	public void fullCopyDtoToModel(ChronometerPartDto dto, ChronometerPart model){
		model.setSeconds(dto.getSeconds());
		model.setDescription(dto.getDescription());
	}

	@Override
	public void safeCopyDtoToModel(ChronometerPartDto dto, ChronometerPart model){
		if(dto.getSeconds() != null) model.setSeconds(dto.getSeconds());
		if(dto.getDescription() != null) model.setDescription(dto.getDescription());
	}
	
	

}
