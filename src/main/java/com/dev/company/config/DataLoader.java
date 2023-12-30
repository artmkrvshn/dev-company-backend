package com.dev.company.config;

import com.dev.company.entity.*;
import com.dev.company.repository.*;
import com.dev.company.service.ProjectService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DataLoader {

    @Autowired
    private EntityManager entityManager;
    private final ProjectService projectService;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final StatusRepository statusRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final Random random = new Random();

    public DataLoader(ProjectService projectService, DepartmentRepository departmentRepository, PositionRepository positionRepository, StatusRepository statusRepository, EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
        this.statusRepository = statusRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

//    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            List<Department> departments = initDepartments();
            List<Position> positions = initPositions();
            List<Status> statuses = initStatuses();
            List<Employee> employees = initEmployees(departments, positions);
            List<Project> projects = initProjects(statuses, employees);
//            initChiefs();
        };
    }

    private List<Department> initDepartments() {
        long count = departmentRepository.count();
        if (count == 0) {
            List<String> names = List.of("HR", "IT", "Finance", "Marketing", "Operations",
                    "Sales", "Customer Service", "Research", "Legal", "Engineering");

            List<Department> departments = names.stream().map(name -> Department.builder().name(name).build()).toList();
            departmentRepository.saveAll(departments);
        }
        return departmentRepository.findAll();
    }

    private List<Position> initPositions() {
        long count = positionRepository.count();
        if (count == 0) {
            List<String> names = List.of("Manager", "Developer", "Analyst", "Coordinator", "Specialist",
                    "Supervisor", "Consultant", "Engineer", "Associate", "Director");

            List<Position> positions = names.stream().map(name -> Position.builder().name(name).build()).toList();
            positionRepository.saveAll(positions);
        }
        return positionRepository.findAll();
    }

    private List<Status> initStatuses() {
        long count = statusRepository.count();
        if (count == 0) {
            List<String> names = List.of("Not started", "Active", "Finished", "On Hold", "Testing Phase");
            List<Status> statuses = names.stream().map(name -> Status.builder().name(name).build()).toList();
            statusRepository.saveAll(statuses);
        }
        return statusRepository.findAll();
    }

    private List<Employee> initEmployees(List<Department> departments, List<Position> positions) {
        long count = employeeRepository.count();
        if (count == 0) {
            List<String> names = List.of("John", "Jane", "Robert", "Mary", "David", "Emily", "Michael", "Olivia", "William", "Sophia");
            List<String> surnames = List.of("Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor");

            List<Employee> employees = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                String name = (String) getRandomElement(names);
                String surname = (String) getRandomElement(surnames);
                double salary = random.nextDouble(50000) + 50000;
                Department department = (Department) getRandomElement(departments);
                Position position = (Position) getRandomElement(positions);
//                Employee employee = new Employee(name, surname, salary, department, position);
//                employees.add(employee);
            }
            employeeRepository.saveAll(employees);
        }
        return employeeRepository.findAll();
    }

    private List<Project> initProjects(List<Status> statuses, List<Employee> empl) {
        long count = projectRepository.count();
        if (count == 0) {
            List<String> names = List.of(
                    "QuantumTech Solutions", "CyberNex Innovations", "CodeHarbor Ventures", "ByteWave Systems", "TechVista Projects",
                    "InnovateHub Technologies", "DataPulse Labs", "CloudSphere Innovations", "SynthCode Dynamics", "CyberForge Innovations",
                    "ApexInvent Tech", "NeoByte Ventures", "QuantumQuotient Projects", "CodeBurst Innovations", "InnovateIQ Solutions",
                    "PixelPulse Technologies", "NexGenCode Innovations", "LogicNex Projects", "Streamline Systems", "ByteCraft Innovations",
                    "QuantumLeap Labs", "CodeFlare Dynamics", "CloudMinds Innovations", "InnovateXpress Technologies", "DataVortex Projects",
                    "InfraPulse Innovations", "CodeMatrix Solutions", "QuantumMesh Dynamics", "SparkSprint Innovations", "TechStrive Ventures"
            );

            List<Project> projects = new ArrayList<>();

            for (String name : names) {
                LocalDate startDate = getRandomDate();
                LocalDate endDate = null;
                if (random.nextBoolean() || random.nextBoolean()) {
                    endDate = getFutureDate(startDate);
                }
                Status status = (Status) getRandomElement(statuses);
                List<Employee> employees = getRandomEmployees(empl).stream().distinct().toList();

                employees.stream().map(Employee::getName).forEach(System.out::println);

                Project project = new Project(null, name, startDate, endDate, status, null, employees);
                projects.add(project);
            }
            projectRepository.saveAll(projects);
        }
        return projectRepository.findAll();
    }

//    private void initChiefs() {
//        List<Project> projects = projectService.readAll();
//        projects.forEach(project -> {
//            System.out.println(project.getEmployees());
//            List<Employee> employees = project.getEmployees();
//            int id = random.nextInt(employees.size());
//            project.setChief(employees.get(id));
//            projectRepository.save(project);
//        });
//    }

    private Object getRandomElement(List<?> list) {
        int num = random.nextInt(list.size());
        return list.get(num);
    }

    private LocalDate getRandomDate() {
        int minYear = 2020;
        int maxYear = LocalDate.now().getYear();
        int year = random.nextInt(maxYear - minYear) + minYear;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(25) + 1;
        return LocalDate.of(year, month, day);
    }

    private LocalDate getFutureDate(LocalDate date) {
        int days = random.nextInt(10) + 10;
        int weeks = random.nextInt(10);
        int months = random.nextInt(3);
        return date.plusMonths(months).plusWeeks(weeks).plusDays(days);
    }

    private List<Employee> getRandomEmployees(List<Employee> employees) {
        List<Employee> employeeList = new ArrayList<>();
        int teamSize = random.nextInt(20) + 5;
        if (employees.size() < teamSize) {
            teamSize = employees.size();
        }
        for (int i = 0; i < teamSize; i++) {
            int randomNum = random.nextInt(employees.size());
            employeeList.add(employees.get(randomNum));
        }
        return employeeList;
    }
}