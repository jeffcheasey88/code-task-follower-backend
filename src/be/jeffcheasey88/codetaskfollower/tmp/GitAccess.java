package be.jeffcheasey88.codetaskfollower.tmp;

import static be.jeffcheasey88.codetaskfollower.tmp.TemporalRepository.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dev.peerat.framework.dependency.Injection;

@Injection
public class GitAccess{
	
	public record NamedProject(int id, String name){}
	public record Repository(int id, String url, String token, boolean isGlobal, List<NamedProject> projects){}
	public record LightRepository(int id, String url, String token){}
	
	public List<Repository> getRepositories(){
		Map<Integer, Repository> cache = new HashMap<>();
		return listSelect(
				"SELECT gr.*, p.id as projectId, p.name as projectName FROM git_repository gr LEFT JOIN GitProject gp ON gp.gitId = gr.id LEFT JOIN projects p ON p.id = gp.projectId",
					r -> {
						Repository repository = cache.computeIfAbsent(r.getInt("id"), id -> {
							try {
								return new Repository(
										id,
										r.getString("repo_url"),
										r.getString("repo_token"),
										r.getBoolean("is_global"),
										new LinkedList<>()
								);
							}catch(Exception e){
								throw new RuntimeException("??", e);
							}
						});
						String projectName = r.getString("projectName");
						if(projectName != null) repository.projects().add(new NamedProject(r.getInt("projectId"), projectName));
						return repository;
					}
				);
	}
	
	public List<LightRepository> getRepositories(int projectId){
		return listSelect(
				"SELECT gr.* FROM git_repository gr JOIN GitProject gp ON gp.gitId = gr.id WHERE gp.projectId = ?",
				r -> new LightRepository(r.getInt("id"), r.getString("repo_url"), r.getString("repo_token")),
				param(projectId)
			);
	}
	
	public LightRepository addGlobalRepository(String url, String token){
		int id = insertKey(
					"git_repository",
					List.of("repo_url", "repo_token","is_global"),
					param(url),
					param(token),
					param(true)
				);
		return new LightRepository(id, url, token);
	}
	
	public LightRepository addProjectRepository(String url, String token, int projectId){
		int id = insertKey(
					"git_repository",
					List.of("repo_url", "repo_token","is_global"),
					param(url),
					param(token),
					param(false)
				);
		insert("GitProject", List.of("projectId","gitId"), param(projectId), param(id));
		return new LightRepository(id, url, token);
	}
}
