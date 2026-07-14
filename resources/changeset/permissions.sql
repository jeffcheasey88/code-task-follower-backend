1

DROP TABLE player_permissions;

CREATE TABLE playerGroups(
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
	name varchar(255) NOT NULL
);

CREATE TABLE PlayerGroup(
	playerId INTEGER,
    groupId INTEGER,
    PRIMARY KEY (playerId, groupId),
    CONSTRAINT fk_playergroup_player FOREIGN KEY (playerId) REFERENCES players(id),
    CONSTRAINT fk_playergroup_group FOREIGN KEY (groupId) REFERENCES playerGroups(id)
);

CREATE TABLE InheritAccess(
	entityId INTEGER,
	inheritLevel VARCHAR(20),
	PRIMARY KEY (entityId, inheritLevel)
);

CREATE TABLE ProjectAccess(
	roleType VARCHAR(10),
	roleId INTEGER,
	projectId INTEGER,
	accessLevel INTEGER,
	PRIMARY KEY (projectId, roleId, roleType),
	CONSTRAINT fk_projectaccess_project FOREIGN KEY (projectId) REFERENCES projects(id)
);

CREATE TABLE TaskAccess(
	roleType VARCHAR(10),
	roleId INTEGER,
	taskId INTEGER,
	accessLevel INTEGER,
	PRIMARY KEY (taskId, roleId, roleType),
	CONSTRAINT fk_taskaccess_task FOREIGN KEY (taskId) REFERENCES tasks(id)
);

CREATE TABLE StateAccess(
	roleType VARCHAR(10),
	roleId INTEGER,
	stateId INTEGER,
	accessLevel INTEGER,
	PRIMARY KEY (stateId, roleId, roleType),
	CONSTRAINT fk_stateaccess_state FOREIGN KEY (stateId) REFERENCES states(id)
);

CREATE TABLE TagAccess(
	roleType VARCHAR(10),
	roleId INTEGER,
	tagId INTEGER,
	accessLevel INTEGER,
	PRIMARY KEY (tagId, roleId, roleType),
	CONSTRAINT fk_tagaccess_tag FOREIGN KEY (tagId) REFERENCES tags(id)
);