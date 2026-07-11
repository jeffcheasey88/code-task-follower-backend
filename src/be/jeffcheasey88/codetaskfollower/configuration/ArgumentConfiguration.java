package be.jeffcheasey88.codetaskfollower.configuration;

public class ArgumentConfiguration{
	
	private String[] args;
	
	public ArgumentConfiguration(String[] args){
		this.args = args;
	}
	
	public void loadConfig(Configuration config) throws Exception{
		if(args == null || args.length == 0) return;
		for(String argument : args){
			int index = argument.indexOf('=');
			if(index < 0){
				config.processAlias(argument);
			}else{
				String key = argument.substring(0, index);
				String value = argument.substring(index+1);
				config.setValue(key, value);
			}
		}
	}
}
