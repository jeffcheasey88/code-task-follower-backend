package be.jeffcheasey88.codetaskfollower.dto;

import java.util.List;

public class CommitDto{
	private Integer id;
	private String hash;
	private List<TaskDto> tasks;
	public CommitDto(Integer id, String hash, List<TaskDto> tasks){
		this.id = id;
		this.hash = hash;
		this.tasks = tasks;
	}
}
