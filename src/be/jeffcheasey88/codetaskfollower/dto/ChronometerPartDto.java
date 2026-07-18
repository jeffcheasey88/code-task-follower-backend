package be.jeffcheasey88.codetaskfollower.dto;

public class ChronometerPartDto{
	private Integer id;
	private Integer seconds;
	private String description;
	public ChronometerPartDto(Integer id, Integer seconds, String description){
		this.id = id;
		this.seconds = seconds;
		this.description = description;
	}
	
	public Integer getId(){
		return this.id;
	}
	
	public Integer getSeconds(){
		return this.seconds;
	}
	
	public String getDescription(){
		return this.description;
	}
	
}
