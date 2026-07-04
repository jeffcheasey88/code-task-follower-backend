package be.jeffcheasey88.codetaskfollower.dto;

public class ChronometerPartDto{
	private Integer id;
	private Integer seconds;
	private String description;
	private ChronometerDto chronometer;
	public ChronometerPartDto(Integer id, Integer seconds, String description, ChronometerDto chronometer){
		this.id = id;
		this.seconds = seconds;
		this.description = description;
		this.chronometer = chronometer;
	}
}
