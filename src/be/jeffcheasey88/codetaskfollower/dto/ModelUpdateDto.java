package be.jeffcheasey88.codetaskfollower.dto;

import be.jeffcheasey88.codetaskfollower.model.Model;
import dev.peerat.framework.json.JsonMap;

public class ModelUpdateDto{
	
	private Model model;
	private String action;
	
	public ModelUpdateDto(Model model, String action){
		this.model = model;
		this.action = action;
	}
	
	public Model getModel(){
		return this.model;
	}
	
	public void fillJson(JsonMap json){
		json.set("modelType", model.getClass().getSimpleName());
		json.set("modelAction", action);
	}

}
