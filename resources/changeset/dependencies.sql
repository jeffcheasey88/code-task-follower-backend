3

CREATE TABLE TaskDependencies(
	baseId INTEGER,
    dependId INTEGER,
    PRIMARY KEY (baseId, dependId),
    CONSTRAINT fk_taskdependencies_base FOREIGN KEY (baseId) REFERENCES tasks(id),
    CONSTRAINT fk_taskdependencies_depend FOREIGN KEY (dependId) REFERENCES tasks(id)
);