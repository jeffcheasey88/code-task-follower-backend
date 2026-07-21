2

CREATE TABLE git_repository(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	repo_url VARCHAR(255) NOT NULL,
	repo_token VARCHAR(255) NOT NULL,
	is_global BOOLEAN NOT NULL
); 

CREATE TABLE GitProject(
	projectId INTEGER,
    gitId    INTEGER,
    PRIMARY KEY (projectId, gitId),
    CONSTRAINT fk_gitproject_project FOREIGN KEY (projectId) REFERENCES projects (id),
    CONSTRAINT fk_gitproject_git FOREIGN KEY (gitId) REFERENCES git_repository (id)
);

CREATE TABLE PlayerGitName(
	playerId INTEGER,
    gitId    INTEGER,
    gitName  VARCHAR(50),
    PRIMARY KEY (playerId, gitId),
    CONSTRAINT fk_playergitname_player FOREIGN KEY (playerId) REFERENCES players (id),
    CONSTRAINT fk_playergitname_git FOREIGN KEY (gitId) REFERENCES git_repository (id)
);

CREATE TABLE PlayerCurrentlyDev(
	playerId INTEGER,
	stateId INTEGER,
	PRIMARY KEY (playerId, stateId),
    CONSTRAINT fk_playerstate_player FOREIGN KEY (playerId) REFERENCES players (id),
    CONSTRAINT fk_playerstate_state FOREIGN KEY (stateId) REFERENCES states (id)
);