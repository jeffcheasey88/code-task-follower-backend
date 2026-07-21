package be.jeffcheasey88.codetaskfollower.controller;

import static dev.peerat.framework.RequestType.POST;

import be.jeffcheasey88.codetaskfollower.configuration.Authenticator.GitRepository;
import dev.peerat.framework.HttpReader;
import dev.peerat.framework.routes.Route;

public class GitController{
	
	//userId can be username
	
	// GET /git/repositories/ -> list all (admin)
	// GET /git/repositories/<projectId> -> list all from a project (project admin)
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
	
	@Route(path = "/webhook", type = POST)
	public void onWebHook(GitRepository repository, HttpReader reader) throws Exception{
		System.out.println("webhook:");
		System.out.println(reader.readJson());
		System.out.println("webhook;");
	}

}
