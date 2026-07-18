package be.jeffcheasey88.codetaskfollower.dto;

import java.util.List;

public class ChronometerDto{
	private Integer id;
	private Integer seconds;
	private List<ChronometerPartDto> parts;
	public ChronometerDto(Integer id, Integer seconds, List<ChronometerPartDto> parts){
		this.id = id;
		this.seconds = seconds;
		this.parts = parts;
	}
	
	public Integer getId(){
		return this.id;
	}
	
	public Integer getSeconds(){
		return this.seconds;
	}
	
	public List<ChronometerPartDto> getParts(){
		return this.parts;
	}
}
