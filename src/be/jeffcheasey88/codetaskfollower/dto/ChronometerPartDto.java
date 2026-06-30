package be.jeffcheasey88.codetaskfollower.dto;

public class ChronometerPartDto{
	private int id;
	private int seconds;
	private String description;
	private ChronometerDto chronometer;
	public ChronometerPartDto(int id, int seconds, String description, ChronometerDto chronometer){
		this.id = id;
		this.seconds = seconds;
		this.description = description;
		this.chronometer = chronometer;
	}
}
