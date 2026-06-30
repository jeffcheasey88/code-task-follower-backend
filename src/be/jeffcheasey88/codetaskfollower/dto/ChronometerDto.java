package be.jeffcheasey88.codetaskfollower.dto;

import java.util.List;

public class ChronometerDto{
	private int id;
	private int seconds;
	private List<ChronometerPartDto> parts;
	public ChronometerDto(int id, int seconds, List<ChronometerPartDto> parts){
		this.id = id;
		this.seconds = seconds;
		this.parts = parts;
	}
}
