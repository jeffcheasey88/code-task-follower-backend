CREATE TABLE projects(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255) NOT NULL,
    color varchar(255),
    description text
);

CREATE TABLE states(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255) NOT NULL,
    color varchar(255)
);

CREATE TABLE ProjectStates(
    projectId INTEGER,
    stateId    INTEGER,
    PRIMARY KEY (projectId, stateId),
    CONSTRAINT fk_projectstate_project FOREIGN KEY (projectId) REFERENCES projects (id),
    CONSTRAINT fk_projectstate_state FOREIGN KEY (stateId) REFERENCES states (id)
);

CREATE TABLE tasks(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255) NOT NULL,
    stateId INTEGER,
    estimateSeconds INTEGER,
    description text,
    CONSTRAINT fk_task_state FOREIGN KEY (stateId) REFERENCES states(id)
);

CREATE TABLE TaskProject(
    projectId INTEGER,
    taskId    INTEGER,
    PRIMARY KEY (projectId, taskId),
    CONSTRAINT fk_taskproject_project FOREIGN KEY (projectId) REFERENCES projects (id),
    CONSTRAINT fk_taskproject_task FOREIGN KEY (taskId) REFERENCES tasks (id)
);

CREATE TABLE commits(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    hash varchar(255) NOT NULL
);

CREATE TABLE CommitTask(
    commitId INTEGER,
    taskId   INTEGER,
    PRIMARY KEY (commitId, taskId),
    CONSTRAINT fk_committask_commit FOREIGN KEY (commitId) REFERENCES commits (id),
    CONSTRAINT fk_committask_task FOREIGN KEY (taskId) REFERENCES tasks (id)
);

CREATE TABLE chronometers(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    seconds INTEGER,
    taskId INTEGER,
    CONSTRAINT fk_chronometer_task FOREIGN KEY (taskId) REFERENCES tasks(id)
);

CREATE TABLE chronometerparts(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    seconds INTEGER,
    description varchar(255),
    chronometerId INTEGER,
    CONSTRAINT fk_chronometerpart_chronometer FOREIGN KEY (chronometerId) REFERENCES chronometers(id)
);

CREATE TABLE branches(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    repositoryName varchar(255) NOT NULL,
    branchName varchar(255)
);

CREATE TABLE TaskBranch(
    taskId INTEGER,
    branchId INTEGER,
    PRIMARY KEY (taskId, branchId),
    CONSTRAINT fk_taskbranch_task FOREIGN KEY (taskId) REFERENCES tasks(id),
    CONSTRAINT fk_taskbranch_branch FOREIGN KEY (branchId) REFERENCES branches(id)
);

CREATE TABLE ProjectBranch(
    projectId INTEGER,
    branchId INTEGER,
    PRIMARY KEY (projectId, branchId),
    CONSTRAINT fk_projectbranch_project FOREIGN KEY (projectId) REFERENCES projects(id),
    CONSTRAINT fk_projectbranch_branch FOREIGN KEY (branchId) REFERENCES branches(id)
);

CREATE TABLE tags(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255) NOT NULL,
    color varchar(255)
);

CREATE TABLE TaskTag(
    taskId INTEGER,
    tagId INTEGER,
    PRIMARY KEY (taskId, tagId),
    CONSTRAINT fk_tasktag_task FOREIGN KEY (taskId) REFERENCES tasks(id),
    CONSTRAINT fk_tasktag_tag FOREIGN KEY (tagId) REFERENCES tags(id)
);

CREATE TABLE codes(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    branch INTEGER,
    classPath varchar(255),
    CONSTRAINT fk_code_branch FOREIGN KEY (branch) REFERENCES branches(id)
);

CREATE TABLE TaskCode(
    taskId INTEGER,
    codeId INTEGER,
    PRIMARY KEY (taskId, codeId),
    CONSTRAINT fk_taskcode_task FOREIGN KEY (taskId) REFERENCES tasks(id),
    CONSTRAINT fk_taskcode_code FOREIGN KEY (codeId) REFERENCES codes(id)
);

CREATE TABLE features(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	name varchar(255) NOT NULL,
	description varchar(255),
	version INTEGER
);

CREATE TABLE players(
	id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	username varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	isAdmin boolean
);