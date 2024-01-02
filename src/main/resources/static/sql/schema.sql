DROP TABLE IF EXISTS project_employees;
DROP TABLE IF EXISTS project_chief;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS status;

CREATE TABLE department
(
    id   BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE position
(
    id   BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE status
(
    id   BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE employee
(
    id            BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    name          VARCHAR(30)  NOT NULL,
    surname       VARCHAR(30)  NOT NULL,
    salary        FLOAT        NOT NULL CHECK ( salary >= 0 ),
    email         VARCHAR(100) NOT NULL,
    password      VARCHAR(100) NOT NULL,
    department_id BINARY(16) REFERENCES department (id),
    position_id   BINARY(16) REFERENCES position (id)
);

CREATE TABLE project
(
    id         BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    name       VARCHAR(30) NOT NULL UNIQUE,
    start_date DATE,
    end_date   DATE,
    status_id  BINARY(16) REFERENCES status (id)
);

CREATE TABLE project_employees
(
    id          BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    project_id  BINARY(16) NOT NULL REFERENCES project (id),
    employee_id BINARY(16) NOT NULL REFERENCES employee (id),
    UNIQUE (project_id, employee_id)
);

CREATE TABLE project_chief
(
    id          BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),
    project_id  BINARY(16) UNIQUE REFERENCES project (id),
    employee_id BINARY(16) UNIQUE REFERENCES employee (id)
);

DROP PROCEDURE IF EXISTS check_single_chief;
CREATE PROCEDURE check_single_chief(_project_id binary(16), _employee_id binary(16))
BEGIN
    IF NOT EXISTS(SELECT 1
                  FROM project_employees pe
                  WHERE pe.project_id = _project_id
                    AND pe.employee_id = _employee_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'A chief should work on this project';
    END IF;
END;

DROP TRIGGER IF EXISTS project_chief_insert;
CREATE TRIGGER project_chief_insert
    BEFORE INSERT
    ON project_chief
    FOR EACH ROW
BEGIN
    CALL check_single_chief(NEW.project_id, NEW.employee_id);
END;



DROP TRIGGER IF EXISTS project_chief_update;
CREATE TRIGGER project_chief_update
    BEFORE UPDATE
    ON project_chief
    FOR EACH ROW
BEGIN
    CALL check_single_chief(NEW.project_id, NEW.employee_id);
END;



DROP FUNCTION IF EXISTS check_employee_count_by_department_uuid;
CREATE FUNCTION check_employee_count_by_department_uuid(_department_id binary) RETURNS BOOLEAN
    DETERMINISTIC
BEGIN
    DECLARE employee_count INT;
    SELECT count(*) INTO employee_count FROM employee e WHERE e.department_id = _department_id;
    RETURN employee_count > 0;
END;



DROP FUNCTION IF EXISTS check_project_count_by_status_uuid;
CREATE FUNCTION check_project_count_by_status_uuid(_status_id binary) RETURNS BOOLEAN
    DETERMINISTIC
BEGIN
    DECLARE project_count INT;
    SELECT COUNT(*) INTO project_count FROM project p WHERE p.status_id = _status_id;
    RETURN project_count > 0;
END;



DROP FUNCTION IF EXISTS check_employee_count_by_position_uuid;
CREATE FUNCTION check_employee_count_by_position_uuid(_position_id binary) RETURNS BOOLEAN
    DETERMINISTIC
BEGIN
    DECLARE employee_count INT;
    SELECT COUNT(*) INTO employee_count FROM employee e WHERE e.position_id = _position_id;
    RETURN employee_count > 0;
END;

DROP PROCEDURE password_encrypt;
CREATE PROCEDURE password_encrypt(IN enc_key varchar(20))
BEGIN
    UPDATE employee e
    SET e.password = HEX(AES_ENCRYPT(e.password, enc_key));
END;

CALL password_encrypt('ENC_KEY');

DROP PROCEDURE password_decrypt;
CREATE PROCEDURE password_decrypt(IN enc_key VARCHAR(20))
BEGIN
    UPDATE employee
    SET password = CAST(AES_DECRYPT(UNHEX(password), enc_key) AS CHAR);
END;

CALL password_decrypt('ENC_KEY');


CREATE USER 'database_admin'@'%' IDENTIFIED WITH mysql_native_password BY 'admin';
GRANT Alter, Alter Routine, Create, Create Routine, Create Temporary Tables, Create User, Create View, Delete, Drop, Event, Execute, File, Grant Option, Index, Insert, Lock Tables, Process, References, Reload, Replication Client, Replication Slave, Select, Show Databases, Show View, Shutdown, Super, Trigger, Update ON *.* TO `database_admin`@`%`;

CREATE USER 'database_employee'@'%' IDENTIFIED BY 'employee';
GRANT SELECT ON * TO 'database_employee'@'%';

CREATE USER 'database_manager'@'%' IDENTIFIED BY 'manager';
GRANT SELECT, INSERT, UPDATE, DELETE ON * TO 'manager'@'%';

SHOW GRANTS FOR database_admin;
SHOW GRANTS FOR database_emsployee;
SHOW GRANTS FOR database_manager;