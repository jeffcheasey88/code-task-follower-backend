package be.jeffcheasey88.codetaskfollower.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileConfiguration{
	
	private File file;
	
	public FileConfiguration(File file){
		this.file = file;
	}
	
	public void loadConfig(Configuration config) throws Exception{
		if(!file.exists()) return;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = reader.readLine();
		while(line != null){
			int index = line.indexOf('=');
			if(index < 0){
				config.processAlias(line);
			}else{
				String key = line.substring(0, index);
				String value = line.substring(index+1);
				config.setValue(key, value);
			}
			line = reader.readLine();
		}
		reader.close();
	}

}
