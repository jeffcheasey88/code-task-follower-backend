package be.jeffcheasey88.codetaskfollower.model;

import java.util.List;

import dev.peerat.mapping.Key;
import dev.peerat.mapping.Treasure;
import dev.peerat.mapping.TreasureCache;

@Treasure
public class State{

	@Key(auto=true) private int id;
	private String name;
	private String color;
	
	public State(int id, String name, String color){
		this.id = id;
		this.name = name;
		this.color = color;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setColor(String color){
		this.color = color;
	}
	
	public String getColor(){
		return this.color;
	}
	
	
	public static List<State> getStates(){
		return TreasureCache.<State>selectAll().toList();
	}
	
	public static State getState(int id){
		return TreasureCache.<State>selectAll().filter(state -> state.id == id).get();
	}
}
