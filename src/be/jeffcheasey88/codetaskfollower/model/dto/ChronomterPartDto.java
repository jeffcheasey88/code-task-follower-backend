package be.jeffcheasey88.codetaskfollower.model.dto;

public class ChronomterPartDto{
	private int id;
	private int seconds;
	private String description;
	private ChronometerDto chronometer;
	public ChronomterPartDto(int id, int seconds, String description, ChronometerDto chronometer){
		this.id = id;
		this.seconds = seconds;
		this.description = description;
		this.chronometer = chronometer;
	}
}
