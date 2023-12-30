package com.dev.company.repository;

import com.dev.company.entity.Department;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void contextLoads() {
        assertThat(departmentRepository).isNotNull();
    }

    @Test
    void givenValidId_whenReadById_thenReturnDepartment() {
        Department expected = new Department(null, "Department", Collections.emptyList());

        departmentRepository.saveAndFlush(expected);

        Optional<Department> result = departmentRepository.readById(expected.getId());

        assertTrue(result.isPresent());
        assertThat(result).isNotEmpty();

        Department actual = result.get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmployees(), actual.getEmployees());
    }

    @Test
    void givenInvalidId_whenReadById_thenReturnOptionalEmpty() {
        Optional<Department> result = departmentRepository.readById(99999L);

        assertFalse(result.isPresent());
        assertThat(result).isEmpty();
    }

    @Test
    void givenDepartments_whenReadAllOrderById_thenReturnAllOrderedById() {
        List<Department> departments = List.of(
                new Department(null, "Dep 1", Collections.emptyList()),
                new Department(null, "Dep 2", Collections.emptyList()),
                new Department(null, "Dep 3", Collections.emptyList())
        );

        departmentRepository.saveAll(departments);

        List<Department> result = departmentRepository.readAllOrderById();
        List<Department> expected = departmentRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        assertEquals(3, result.size());
        assertEquals(expected, result);
    }

    @Test
    void givenValidName_whenExistsByName_thenReturnTrue() {
        Department department = new Department(null, "Dep 1", Collections.emptyList());

        departmentRepository.save(department);

        boolean result = departmentRepository.existsByName(department.getName());

        assertTrue(result);
    }

    @Test
    void givenInvalidName_whenExistsByName_thenReturnFalse() {
        boolean result = departmentRepository.existsByName(Mockito.anyString());

        assertFalse(result);
    }

}