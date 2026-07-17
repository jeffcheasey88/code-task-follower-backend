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

CREATE VIEW ProjectAccessView AS 
SELECT pa.roleId as playerId, pa.projectId, pa.accessLevel 
FROM ProjectAccess pa 
WHERE pa.roleType = 'player' 

UNION ALL 

SELECT pg.playerId as playerId, pa.projectId, pa.accessLevel 
FROM ProjectAccess pa 
JOIN PlayerGroup pg ON pg.groupId = pa.roleId 
WHERE pa.roleType = 'group'
;

CREATE VIEW TaskAccessView AS 
SELECT ta.roleId as playerId, ta.taskId, ta.accessLevel, 'task' as entityLevel 
FROM TaskAccess ta 
WHERE ta.roleType = 'player' 

UNION ALL 

SELECT pg.playerId as playerId, ta.taskId, ta.accessLevel, 'task' as entityLevel 
FROM TaskAccess ta 
JOIN PlayerGroup pg ON pg.groupId = ta.roleId 
WHERE ta.roleType = 'group'

UNION ALL 

SELECT pav.playerId, tp.taskId, pav.accessLevel, 'project' as entityLevel 
FROM ProjectAccessView pav 
JOIN TaskProject tp ON tp.projectId = pav.projectId
;

CREATE VIEW EntityPermissionView AS 
SELECT pa.roleType as roleType, pa.roleId as roleId, pa.projectId as entityId, pa.accessLevel as accessLevel, 'project' as entityType 
FROM ProjectAccess pa 

UNION ALL 

SELECT 'player' as roleType, pg.playerId as roleId, pa.projectId as entityId, pa.accessLevel as accessLevel, 'project' as entityType 
FROM ProjectAccess pa 
JOIN PlayerGroup pg ON pg.groupId = pa.roleId 
WHERE pa.roleType = 'group' 

UNION ALL 

SELECT ta.roleType as roleType, ta.roleId as roleId, ta.taskId as entityId, ta.accessLevel as accessLevel, 'task' as entityType 
FROM TaskAccess ta 

UNION ALL

SELECT 'player' as roleType, pg.playerId as roleId, ta.taskId as entityId, ta.accessLevel as accessLevel, 'task' as entityType 
FROM TaskAccess ta 
JOIN PlayerGroup pg ON pg.groupId = ta.roleId 
WHERE ta.roleType = 'group' 
;