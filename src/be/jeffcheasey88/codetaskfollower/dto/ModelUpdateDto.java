package be.jeffcheasey88.codetaskfollower.dto;

import dev.peerat.framework.json.JsonMap;

public class ModelUpdateDto{
	
	private Object model;
	private String action;
	
	public ModelUpdateDto(Object model, String action){
		this.model = model;
		this.action = action;
	}
	
	public Object getModel(){
		return this.model;
	}
	
	public void fillJson(JsonMap json){
		json.set("modelType", model.getClass().getSimpleName());
		json.set("modelAction", action);
	}

}
