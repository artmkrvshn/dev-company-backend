INSERT INTO `position`(name)
VALUES ('Tech Lead'),
       ('Team Lead'),
       ('Software Engineer'),
       ('QA Engineer'),
       ('UI/UX Designer'),
       ('Product Manager'),
       ('Software Architect'),
       ('Manager'),
       ('Administrator');

INSERT INTO `status`(name)
VALUES ('Not started'),
       ('Active'),
       ('Finished');

INSERT INTO department (name)
VALUES ('Software Development'),
       ('Quality Assurance'),
       ('Design'),
       ('Support'),
       ('Project Management');

INSERT INTO employee (name, surname, salary, email, password, department_id, position_id)
VALUES ('John', 'Doe', 50000.0, 'john.doe@gmail.com', 'john.doe', (select id from department limit 1),
        (select id from position limit 1)),
       ('Jane', 'Smith', 60000.0, 'jane.smith@gmail.com', 'jane.smith', (select id from department limit 1),
        (select id from position limit 1 OFFSET 1)),
       ('Matthew', 'Anderson', 63000.0, 'matthew.anderson@gmail.com', 'matthew.anderson',
        (select id from department limit 1), (select id from position limit 1 OFFSET 2)),
       ('Sophia', 'Martinez', 51000.0, 'sophia.martinez@gmail.com', 'sophia.martinez',
        (select id from department limit 1), (select id from position limit 1 OFFSET 3)),

       ('Bob', 'Johnson', 55000.0, 'bob.johnson@gmail.com', 'bob.johnson', (select id from department limit 1 OFFSET 1),
        (select id from position limit 1 OFFSET 2)),
       ('Alice', 'Williams', 65000.0, 'alice.williams@gmail.com', 'alice.williams',
        (select id from department limit 1 OFFSET 1), (select id from position limit 1 OFFSET 3)),
       ('Daniel', 'Clark', 59000.0, 'daniel.clark@gmail.com', 'daniel.clark',
        (select id from department limit 1 OFFSET 1), (select id from position limit 1 OFFSET 4)),
       ('Ava', 'Rodriguez', 56000.0, 'ava.rodriguez@gmail.com', 'ava.rodriguez',
        (select id from department limit 1 OFFSET 1), (select id from position limit 1 OFFSET 5)),

       ('David', 'Brown', 70000.0, 'david.brown@gmail.com', 'david.brown', (select id from department limit 1 OFFSET 2),
        (select id from position limit 1 OFFSET 4)),
       ('Emily', 'Davis', 48000.0, 'emily.davis@gmail.com', 'emily.davis', (select id from department limit 1 OFFSET 2),
        (select id from position limit 1 OFFSET 5)),

       ('Michael', 'Jones', 52000.0, 'michael.jones@gmail.com', 'michael.jones',
        (select id from department limit 1 OFFSET 3), (select id from position limit 1 OFFSET 6)),
       ('Sarah', 'Miller', 58000.0, 'sarah.miller@gmail.com', 'sarah.miller',
        (select id from department limit 1 OFFSET 3), (select id from position limit 1 OFFSET 5)),

       ('Chris', 'Wilson', 54000.0, 'chris.wilson@gmail.com', 'chris.wilson',
        (select id from department limit 1 OFFSET 4), (select id from position limit 1)),
       ('Olivia', 'Taylor', 62000.0, 'olivia.taylor@gmail.com', 'olivia.taylor',
        (select id from department limit 1 OFFSET 4), (select id from position limit 1 OFFSET 1));

INSERT INTO `project` (name, start_date, end_date, status_id)
VALUES ('Website Redesign Project', '2023-04-02', '2023-07-10', (select id from status limit 1 OFFSET 2)),
       ('Mobile App Dev Proj', '2023-06-01', NULL, (select id from status limit 1 OFFSET 1)),
       ('Online Bookstore Dev', '2023-07-15', NULL, (select id from status limit 1 OFFSET 1)),
       ('Food Delivery App and Wsite', NULL, NULL, (select id from status limit 1)),
       ('Travel Booking Portal Dev', NULL, NULL, (select id from status limit 1));

INSERT INTO project_employees(project_id, employee_id)
VALUES ((select id from project limit 1), (select id from employee limit 1)),
       ((select id from project limit 1), (select id from employee limit 1 OFFSET 1)),
       ((select id from project limit 1), (select id from employee limit 1 OFFSET 2)),
       ((select id from project limit 1), (select id from employee limit 1 OFFSET 3)),
       ((select id from project limit 1), (select id from employee limit 1 OFFSET 4)),
       ((select id from project limit 1 OFFSET 1), (select id from employee limit 1)),
       ((select id from project limit 1 OFFSET 1), (select id from employee limit 1 OFFSET 1)),
       ((select id from project limit 1 OFFSET 1), (select id from employee limit 1 OFFSET 5)),
       ((select id from project limit 1 OFFSET 1), (select id from employee limit 1 OFFSET 6));

INSERT INTO project_chief(project_id, employee_id)
VALUES ((select id from project limit 1), (select id from employee limit 1 OFFSET 1)),
       ((select id from project limit 1 OFFSET 1), (select id from employee limit 1 OFFSET 6));