CREATE TABLE projects(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name varchar(255),
    color varchar(255),
)

CREATE TABLE tasks(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name varchar(255),
    stateId INTEGER,
    CONSTRAINT fk_state FOREIGN KEY (state) REFERENCES states(id),
)

CREATE TABLE TaskProject{
    projectId INTEGER,
    taskId INTEGER,
    PRIMARY KEY (projectId, taskId),
    CONSTRAINT fk_project FOREIGN KEY (projectId) REFERENCES projects(id),
    CONSTRAINT fk_task FOREIGN KEY (taskId) REFERENCES tasks(id),
}

CREATE TABLE commits(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    hash varchar(255),
)

CREATE TABLE CommitTask{
    commitId INTEGER,
    taskId INTEGER,
    PRIMARY KEY (commitId, taskId),
    CONSTRAINT fk_commit FOREIGN KEY (commitId) REFERENCES commits(id),
    CONSTRAINT fk_task FOREIGN KEY (taskId) REFERENCES tasks(id),
}

CREATE TABLE chronometers(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    seconds INTEGER,
    taskId INTEGER,
    CONSTRAINT fk_task FOREIGN KEY (taskId) REFERENCES tasks(id),
)

CREATE TABLE chronomterparts(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    seconds INTEGER,
    description varchar(255),
    chronometerId INTEGER,
    CONSTRAINT fk_chronometer FOREIGN KEY (chronometerId) REFERENCES chronometers(id),
)

CREATE TABLE branchs(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    repositoryName varchar(255),
    branchName varchar(255),
)

CREATE TABLE TaskBranch{
    taskId INTEGER,
    branchId INTEGER,
    PRIMARY KEY (taskId, branchId),
    CONSTRAINT fk_task FOREIGN KEY (taskId) REFERENCES tasks(id),
    CONSTRAINT fk_branch FOREIGN KEY (branchId) REFERENCES branchs(id),
}

CREATE TABLE ProjectBranch{
    projectId INTEGER,
    branchId INTEGER,
    PRIMARY KEY (projectId, branchId),
    CONSTRAINT fk_project FOREIGN KEY (projectId) REFERENCES projects(id),
    CONSTRAINT fk_branch FOREIGN KEY (branchId) REFERENCES branchs(id),
}

CREATE TABLE states(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name varchar(255),
    color varchar(255),
)

CREATE TABLE TaskState{
    taskId INTEGER,
    stateId INTEGER,
    PRIMARY KEY (taskId, stateId),
    CONSTRAINT fk_task FOREIGN KEY (taskId) REFERENCES tasks(id),
    CONSTRAINT fk_state FOREIGN KEY (stateId) REFERENCES states(id),
}

CREATE TABLE ProjectState{
    projectId INTEGER,
    stateId INTEGER,
    PRIMARY KEY (projectId, stateId),
    CONSTRAINT fk_project FOREIGN KEY (projectId) REFERENCES projects(id),
    CONSTRAINT fk_state FOREIGN KEY (stateId) REFERENCES states(id),
}

CREATE TABLE tags(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name varchar(255),
    color varchar(255),
)

CREATE TABLE TaskTag{
    taskId INTEGER,
    tagId INTEGER,
    PRIMARY KEY (taskId, tagId),
    CONSTRAINT fk_task FOREIGN KEY (taskId) REFERENCES tasks(id),
    CONSTRAINT fk_tag FOREIGN KEY (tagId) REFERENCES tags(id),
}

CREATE TABLE codes(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    branch INTEGER,
    classPath varchar(255),
    CONSTRAINT fk_branch FOREIGN KEY (branch) REFERENCES branchs(id),
)

CREATE TABLE TaskCode{
    taskId INTEGER,
    codeId INTEGER,
    PRIMARY KEY (taskId, codeId),
    CONSTRAINT fk_task FOREIGN KEY (taskId) REFERENCES tasks(id),
    CONSTRAINT fk_code FOREIGN KEY (codeId) REFERENCES codes(id),
}