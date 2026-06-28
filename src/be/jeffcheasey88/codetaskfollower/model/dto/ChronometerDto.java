package be.jeffcheasey88.codetaskfollower.model.dto;

import java.util.List;

public class ChronometerDto{
	private int id;
	private int seconds;
	private List<ChronomterPartDto> parts;
	public ChronometerDto(int id, int seconds, List<ChronomterPartDto> parts){
		this.id = id;
		this.seconds = seconds;
		this.parts = parts;
	}
}
