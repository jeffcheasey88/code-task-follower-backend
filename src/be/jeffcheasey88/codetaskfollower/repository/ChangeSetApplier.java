package be.jeffcheasey88.codetaskfollower.repository;

import static be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository.insert;
import static be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository.param;
import static be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository.select;
import static be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository.update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository;
import dev.peerat.mapping.CursedTreasureException;

public class ChangeSetApplier{
	
	private String folderPath;
	private Set<LogicalChangeSet> changes;
	
	public ChangeSetApplier(String folderPath){
		this.folderPath = folderPath;
		this.changes = new TreeSet<>((a,b) -> (a.getIndexNumber() < b.getIndexNumber()) ? -1 : (a.getIndexNumber() > b.getIndexNumber()) ? 1 : 0);
	}
	
	public void addChangeSet(LogicalChangeSet changeset){
		this.changes.add(changeset);
	}
	
	public void apply() throws Exception{
		List<ChangeSet> changes = new LinkedList<>();
		applyDir("resources/"+folderPath, changes);
		for(ChangeSet change : changes){
			LogicalChangeSet logicalChangeset = null;
			for(LogicalChangeSet changeset : this.changes){
				if(changeset.getIndexNumber() == change.index){
					logicalChangeset = changeset;
					break;
				}
			}
			if(logicalChangeset == null){
				this.changes.add(LogicalChangeSet.of(change));
			}else{
				this.changes.remove(logicalChangeset);
				this.changes.add(LogicalChangeSet.and(logicalChangeset, change));
			}
		}
		
		int currentIndex;
		try{
			currentIndex = select("SELECT indexNumber FROM ctf_change_set", r->r.getInt("indexNumber"));
		}catch(CursedTreasureException ex){
			TemporalRepository.INSTANCE.updateRequest(
					"CREATE TABLE ctf_change_set(id INTEGER PRIMARY KEY, indexNumber INTEGER);"
					);
			insert("ctf_change_set", List.of("id", "indexNumber"), param(0), param(currentIndex = -1000));
		}
		
		int updatedIndex = currentIndex;
		for(LogicalChangeSet change : this.changes){
			if(change.getIndexNumber() <= currentIndex) continue;
			updatedIndex = change.getIndexNumber();
			change.applyBefore();
			change.apply();
			change.applyAfter();
			System.out.println("Updating to changeset "+updatedIndex);
		}
		if(updatedIndex > currentIndex){
			update("ctf_change_set", List.of("indexNumber"), List.of("id"), param(updatedIndex), param(0));
		}else{
			System.out.println("no changeset to upgrade");
		}
	}
	
	private void applyDir(String path, List<ChangeSet> changes) throws Exception{
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
	
	private void applyFile(String path, List<ChangeSet> changes) throws Exception{
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
	
	public static interface LogicalChangeSet{
		
		int getIndexNumber();
		
		void applyBefore();
		void apply();
		void applyAfter();
		
		static LogicalChangeSet of(ChangeSet changeset){
			return new LogicalChangeSet(){
				@Override
				public int getIndexNumber(){
					return changeset.index;
				}
				@Override
				public void applyBefore(){}
				@Override
				public void apply(){
					String[] queries = changeset.query.replace("\t", "").split("\\;\\s+");
					for(String query : queries){
						query = query.replace("\n", "");
						TemporalRepository.INSTANCE.updateRequest(query);
					}
				}
				@Override
				public void applyAfter(){}
			};
		}
		
		static LogicalChangeSet and(LogicalChangeSet base, ChangeSet changeset){
			return new LogicalChangeSet(){
				@Override
				public int getIndexNumber(){
					return changeset.index;
				}
				@Override
				public void applyBefore(){
					base.applyBefore();
				}
				@Override
				public void apply(){
					base.apply();
					String[] queries = changeset.query.replace("\t", "").split("\\;\\s+");
					for(String query : queries){
						query = query.replace("\n", " ");
						TemporalRepository.INSTANCE.updateRequest(query);
					}
				}
				@Override
				public void applyAfter(){
					base.applyAfter();
				}
			};
		}
	}
}
