package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.DELETE;
import static dev.peerat.framework.RequestType.POST;
import static dev.peerat.framework.RequestType.PUT;

import java.util.List;
import java.util.Random;

import be.jeffcheasey88.codetaskfollower.configuration.Authenticator.GitRepository;
import be.jeffcheasey88.codetaskfollower.configuration.Authenticator.User;
import be.jeffcheasey88.codetaskfollower.configuration.ModelBinder.Argument;
import be.jeffcheasey88.codetaskfollower.exception.HttpError;
import be.jeffcheasey88.codetaskfollower.tmp.GitAccess;
import be.jeffcheasey88.codetaskfollower.tmp.GitAccess.LightRepository;
import be.jeffcheasey88.codetaskfollower.tmp.GitAccess.Repository;
import dev.peerat.framework.HttpReader;
import dev.peerat.framework.dependency.Injection;
import dev.peerat.framework.routes.Route;

public class GitController{
	
	@Injection private GitAccess gitAccess;
	private Random random;
	
	public GitController(){
		this.random = new Random();
	}
	
	private String randomKey(){
		String result = "Bearer ";
		int buf = random.nextInt(30);
		for(int i = 0; i < 50+buf; i++){
			char c;
			if(random.nextBoolean()){
				c = addChar('0',random.nextInt(10));
			}else if(random.nextBoolean()){
				c = addChar('a',random.nextInt(25));
			}else if(random.nextBoolean()){
				c = addChar('A',random.nextInt(25));
			}else{
				c = i > 48 ? '1':'-';
			}
			result+=c;
		}
		return result;
	}
	
	private char addChar(char base, int add){
		int buf = base+add;
		return (char) buf;
	}
	
	//userId can be username
	
	// POST /git/repositories/ -> add git (admin)
	// POST /git/repositories/<projectId> -> add to a project (project admin)
	// PUT /git/repositories/access/<repoId>
	// PUT /git/repositories/<projectId>/access/<repoId>
	// DELETE /git/repositories/access/<repoId>
	// DELETE /git/repositories/<projectId>/access/<repoId>
	// GET /git/users/ -> list all users of the connected users
	// GET /git/users/<userId> -> list all users of a selected user
	// POST /git/users/<userId> -> add a git user to a selected user
	// PUT /git/users/<userId>/<gitUserId>
	// DELETE /git/users/<userId>/<gitUserId>
	// GET /git/dev-states/ -> get all dev state of the connecter users
	// GET /git/dev-states/<userId>
	// POST /git/dev-states/				...
	// POST /git/dev-states/<userId>		...
	// PUT /git/dev-states/					...
	// PUT /git/dev-states/<userId>			...
	// DELETE /git/dev-states/				...
	// DELETE /git/dev-states/<userId>		...
	
	record LightRepositoryDto(String url){}
	
	@Route(path = "/webhook", type = POST)
	public void onWebHook(GitRepository repository, HttpReader reader) throws Exception{
		System.out.println("webhook:");
		System.out.println(reader.readJson());
		System.out.println("webhook;");
	}
	
	@Route(path = "/git/repositories/", needLogin = true) // GET /git/repositories/ -> list all (admin)
	public List<Repository> onAdminListRepositories(User user){
		if(!user.isAdmin()) throw new HttpError(403);
		return gitAccess.getRepositories();
	}
	
	@Route(path = "/git/repositories/(\\d+)", needLogin = true) //GET /git/repositories/<projectId> -> list all from a project (project admin)
	public List<LightRepository> onProjectListRepositories(User user, @Argument int projectId){
		if(!ProjectController.canAdminProject(user, projectId)) throw new HttpError(403);
		return gitAccess.getRepositories(projectId);
	}
	
	@Route(path = "/git/repositories/", type = POST, needLogin = true)// POST /git/repositories/ -> add git (admin)
	public LightRepository onAdminAddRepository(User user, LightRepositoryDto repositoryDto){
		if(!user.isAdmin()) throw new HttpError(403);
		return gitAccess.addGlobalRepository(repositoryDto.url(), randomKey());
	}
	
	@Route(path = "/git/repositories/(\\d+)", type = POST, needLogin = true)// POST /git/repositories/<projectId> -> add to a project (project admin)
	public LightRepository onAddProjectRepository(User user, @Argument int projectId, LightRepositoryDto repositoryDto){
		if(!ProjectController.canAdminProject(user, projectId)) throw new HttpError(403);
		return gitAccess.addGlobalRepository(repositoryDto.url(), randomKey());
	}
	
	@Route(path = "/git/repositories/access/(\\d+)", type = PUT, needLogin = true)// PUT /git/repositories/access/<repoId>
	public void onAdminEditRepository(User user){
		if(!user.isAdmin()) throw new HttpError(403);
	}
	
	@Route(path = "/git/repositories/(\\d+)/access/(\\d+)", type = PUT, needLogin = true)// PUT /git/repositories/<projectId>/access/<repoId>
	public void onEditProjectRepository(User user){
		
	}
	
	@Route(path = "/git/repositories/access/(\\d+)", type = DELETE, needLogin = true)// DELETE /git/repositories/access/<repoId>
	public void onAdminRemoveRepository(User user){
		if(!user.isAdmin()) throw new HttpError(403);
	}
	
	@Route(path = "/git/repositories/(\\d+)/access/(\\d+)", type = DELETE, needLogin = true)// DELETE /git/repositories/<projectId>/access/<repoId>
	public void onRemoveProjectRepository(User user){
		
	}
//	
//	@Route(path = "/git/users/", needLogin = true)// GET /git/users/ -> list all users of the connected users
//	public void onGetGitUsers(User user){
//		
//	}
//	
//	@Route(path = "/git/users/(\\d+)", needLogin = true)// GET /git/users/<userId> -> list all users of a selected user
//	public void onGetGitUsers(User user){
//		
//	}

	@Route(path = "/git/users/(\\d+)", type = POST, needLogin = true)// POST /git/users/<userId> -> add a git user to a selected user
	public void onAddGitUsers(User user){
		
	}
	@Route(path = "/git/users/(\\d+)/(\\d+)", type = PUT, needLogin = true)// PUT /git/users/<userId>/<gitUserId>
	public void onEditGitUsers(User user){
		
	}
	@Route(path = "/git/users/(\\d+)/(\\d+)", type = DELETE, needLogin = true)// DELETE /git/users/<userId>/<gitUserId>
	public void onRemoveGitUsers(User user){
		
	}
	@Route(path = "/git/dev-states/", needLogin = true)// GET /git/dev-states/ -> get all dev state of the connecter users
	public void onGetDevState(User user){
		
	}
	@Route(path = "/git/dev-states/(\\d+)", needLogin = true)// GET /git/dev-states/<userId>
	public void onGetUserDevState(User user){
		
	}
	@Route(path = "/git/dev-states/", type = POST, needLogin = true)// POST /git/dev-states/				...
	public void onAddDevState(User user){
		
	}
	@Route(path = "/git/dev-states/(\\d+)", type = POST, needLogin = true)// POST /git/dev-states/<userId>		...
	public void onAddUserDevState(User user){
		
	}
	@Route(path = "/git/dev-states/", type = PUT, needLogin = true)// PUT /git/dev-states/					...
	public void onEditDevState(User user){
		
	}
	@Route(path = "/git/dev-states/(\\d+)", type = PUT, needLogin = true)// PUT /git/dev-states/<userId>			...
	public void onEditUserDevState(User user){
		
	}
	
	@Route(path = "/git/dev-states/", type = DELETE, needLogin = true)// DELETE /git/dev-states/				...
	public void onRemoveDevState(User user){
		
	}
	
	@Route(path = "/git/dev-states/(\\d+)", type = DELETE, needLogin = true)// DELETE /git/dev-states/<userId>		...
	public void onRemoveUserDevState(User user){
		
	}
}
