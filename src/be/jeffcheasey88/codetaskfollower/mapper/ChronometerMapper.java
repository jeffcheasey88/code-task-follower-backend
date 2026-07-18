package be.jeffcheasey88.codetaskfollower.mapper;

import be.jeffcheasey88.codetaskfollower.dto.ChronometerDto;
import be.jeffcheasey88.codetaskfollower.model.Chronometer;
import dev.peerat.framework.dependency.Injection;

@Injection
public class ChronometerMapper extends Mapper<ChronometerDto, ChronometerDto, Chronometer>{
	
	@Injection private ChronometerPartMapper chronometerPartMapper;
	
	@Override
	public ChronometerDto toDto(Chronometer model) {
		return new ChronometerDto(model.getId(), model.getSeconds(), chronometerPartMapper.toDto(model.getParts()));
	}

	@Override
	public ChronometerDto toLightDto(Chronometer model) {
		return toDto(model);
	}

	@Override
	public void fullCopyDtoToModel(ChronometerDto dto, Chronometer model){
		model.setSeconds(dto.getSeconds());
	}

	@Override
	public void safeCopyDtoToModel(ChronometerDto dto, Chronometer model){
		if(dto.getSeconds() != null) model.setSeconds(dto.getSeconds());
	}
	
	

}
