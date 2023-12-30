DROP TABLE IF EXISTS project_employees;
DROP TABLE IF EXISTS project_chief;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS status;

CREATE TABLE department
(
    id   uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE position
(
    id   uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE status
(
    id   uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE employee
(
    id            uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name          VARCHAR NOT NULL,
    surname       VARCHAR NOT NULL,
    salary        FLOAT   NOT NULL CHECK ( salary >= 0 ),
    department_id uuid REFERENCES department (id),
    position_id   uuid REFERENCES position (id)
);

CREATE TABLE project
(
    id         uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name       VARCHAR NOT NULL UNIQUE,
    start_date DATE,
    end_date   DATE,
    status_id  uuid REFERENCES status (id)
);

CREATE TABLE project_employees
(
    id          uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    project_id  uuid NOT NULL REFERENCES project (id),
    employee_id uuid NOT NULL REFERENCES employee (id),
    UNIQUE (project_id, employee_id)
);

CREATE TABLE project_chief
(
    id          uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    project_id  uuid UNIQUE REFERENCES project (id),
    employee_id uuid UNIQUE REFERENCES employee (id)
);

CREATE OR REPLACE FUNCTION check_single_chief() RETURNS TRIGGER AS
$$
BEGIN
    IF NOT EXISTS(SELECT 1
                  FROM project_employees pe
                  WHERE pe.project_id = new.project_id
                    AND pe.employee_id = new.employee_id) THEN
        RAISE EXCEPTION 'A chief should work on this project';
    END IF;
    RETURN new;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER project_chief_insert
    BEFORE INSERT OR UPDATE
    ON project_chief
    FOR EACH ROW
EXECUTE FUNCTION check_single_chief();


CREATE OR REPLACE FUNCTION check_employee_count_by_department_id(_department_id uuid)
    RETURNS BOOLEAN AS
$$
DECLARE
    employee_count INT;
BEGIN
    SELECT COUNT(e)
    INTO employee_count
    FROM employee e
    WHERE e.department_id = _department_id;

    RETURN employee_count > 0;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION check_project_count_by_status_uuid(_status_id uuid)
    RETURNS BOOLEAN AS
$$
DECLARE
    project_count INT;
BEGIN
    SELECT COUNT(p)
    INTO project_count
    FROM project p
    WHERE p.status_id = _status_id;
    RETURN project_count > 0;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION check_employee_count_by_position_uuid(_position_id uuid)
    RETURNS BOOLEAN AS
$$
DECLARE
    employee_count INT;
BEGIN
    SELECT COUNT(e)
    INTO employee_count
    FROM employee e
    WHERE e.position_id = _position_id;
    RETURN employee_count > 0;
END;
$$ LANGUAGE plpgsql;

