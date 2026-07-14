package be.jeffcheasey88.codetaskfollower.repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository;
import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository.SqlParam;
import dev.peerat.mapping.CursedTreasureException;

public class ChangeSetApplier{
	
	private String folderPath;
	
	public ChangeSetApplier(String folderPath){
		this.folderPath = folderPath;
	}
	
	public void apply() throws Exception{
		Set<ChangeSet> changes = new TreeSet<>((a,b) -> (a.index < b.index) ? 1 : (a.index > b.index) ? -1 : 0);
		applyDir("resources/"+folderPath, changes);
		
		List<Integer> lastIndex = null;
		try{
			lastIndex = TemporalRepository.INSTANCE.externalSelectRequest("SELECT indexNumber FROM ctf_change_set", r -> {
				try {
					return r.getInt("indexNumber");
				} catch (SQLException e) {}
				return null;
			});
		}catch(CursedTreasureException exception){}
		
		int currentIndex = -1000;
		if(lastIndex == null || lastIndex.isEmpty() || lastIndex.get(0) == null){
			TemporalRepository.INSTANCE.externalUpdateRequest(
					"CREATE TABLE ctf_change_set(id INTEGER PRIMARY KEY, indexNumber INTEGER);"
					);
			TemporalRepository.INSTANCE.externalUpdateRequest(
					"INSERT INTO ctf_change_set (id, indexNumber) VALUES (?,?);",
					new SqlParam("int", 0),
					new SqlParam("int", -1000)
					);
		}else{
			currentIndex = lastIndex.get(0);
		}
		
		int updatedIndex = currentIndex;
		for(ChangeSet change : changes){
			if(change.index <= currentIndex) continue;
			updatedIndex = change.index;
			String[] queries = change.query.replace("\t", "").split("\\;\\s+");
			for(String query : queries){
				query = query.replace("\n", "");
				TemporalRepository.INSTANCE.externalUpdateRequest(query);
			}
			System.out.println("Updating to changeset "+change.index);
		}
		if(updatedIndex > currentIndex){
			TemporalRepository.INSTANCE.externalUpdateRequest(
					"UPDATE ctf_change_set SET indexNumber = ? WHERE id = ?",
					new SqlParam("int", updatedIndex),
					new SqlParam("int", 0)
					);
		}else{
			System.out.println("no changeset to upgrade");
		}
	}
	
	private void applyDir(String path, Set<ChangeSet> changes) throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path)));
		String line = reader.readLine();
		List<String> lines = new LinkedList<>();
		while(line != null){
			lines.add(line);
			line = reader.readLine();
		}
		reader.close();
		for(String linePath : lines){
			if(linePath.contains(".")){
				applyFile(path+linePath, changes);
			}else{
				applyDir(path+linePath+"/", changes);
			}
		}
	}
	
	private void applyFile(String path, Set<ChangeSet> changes) throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path)));
		String line = reader.readLine();
		int index = Integer.parseInt(line);
		line = reader.readLine();
		String query = line;
		while(line != null){
			query+=line+"\n";
			line = reader.readLine();
		}
		reader.close();
		changes.add(new ChangeSet(index, query));
	}
	
	private record ChangeSet(int index, String query){}
}
