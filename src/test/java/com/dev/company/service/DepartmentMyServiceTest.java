//package com.dev.company.service;
//
//import com.dev.company.entity.Department;
//import com.dev.company.exception.department.DepartmentNotFoundException;
//import com.dev.company.repository.DepartmentRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.BDDMockito.*;
//
//@SpringBootTest
//public class DepartmentMyServiceTest {
//
//    @InjectMocks
//    private DepartmentService service;
//
//    @Mock
//    private DepartmentRepository repository;
//
//    @Test
//    void contextLoads() {
//        assertThat(service).isNotNull();
//        assertThat(repository).isNotNull();
//    }
//
//    @Test
//    void givenDepartment_whenCreate_thenSaveAndFlush() {
//        Department department = new Department(1L, "Department", null);
//        when(repository.saveAndFlush(department)).thenReturn(department);
//
//        Long savedId = service.create(department);
//
//        verify(repository, times(1)).saveAndFlush(department);
//        assertEquals(1, savedId);
//    }
//
//    @Test
//    void givenValidId_whenReadById_thenReturnDepartment() {
//        Department department = new Department(1L, "Department", Collections.emptyList());
//        long id = department.getId();
//        when(repository.readById(id)).thenReturn(Optional.of(department));
//
//        Department result = service.readById(id);
//
//        verify(repository, times(1)).readById(id);
//        assertEquals(department, result);
//    }
//
//    @Test
//    void givenInvalidId_whenReadById_thenThrowDepartmentNotFoundException() {
//        long invalidId = 99999;
//        when(repository.readById(invalidId)).thenReturn(Optional.empty());
//
//        assertThrows(DepartmentNotFoundException.class, () -> service.readById(invalidId));
//
//        verify(repository, times(1)).readById(invalidId);
//    }
//
//    @Test
//    void givenDepartments_whenReadAllOrderById_thenReadAllOrderById() {
//        List<Department> departments = List.of(
//                new Department(1L, "D1", Collections.emptyList()),
//                new Department(2L, "D2", Collections.emptyList()),
//                new Department(3L, "D3", Collections.emptyList()));
//        when(repository.readAllOrderById()).thenReturn(departments);
//
//        List<Department> result = service.readAll();
//
//        verify(repository, times(1)).readAllOrderById();
//        assertEquals(departments, result);
//    }
//
//    @Test
//    void givenValidId_whenUpdate_thenSave() {
//        Department department = new Department(1L, "Department", Collections.emptyList());
//        long validId = department.getId();
//        when(repository.existsById(validId)).thenReturn(true);
//        when(repository.save(department)).thenReturn(department);
//
//        service.updateById(validId, department);
//
//        verify(repository, times(1)).existsById(validId);
//        verify(repository, times(1)).save(department);
//    }
//
//    @Test
//    void givenInvalidId_whenUpdate_thenThrowDepartmentNotFoundException() {
//        long invalidId = 99999;
//        when(repository.existsById(invalidId)).thenReturn(false);
//
//        assertThrows(DepartmentNotFoundException.class, () -> service.updateById(invalidId, any()));
//
//        verify(repository, times(1)).existsById(invalidId);
//        verify(repository, times(0)).save(any());
//    }
//
//    @Test
//    void givenValidId_whenDelete_thenDelete() {
//        long validId = 1;
//        when(repository.existsById(validId)).thenReturn(true);
//
//        service.deleteById(validId);
//
//        verify(repository, times(1)).existsById(validId);
//        verify(repository, times(1)).deleteById(validId);
//    }
//
//    @Test
//    void givenInvalidId_whenDelete_thenThrowDepartmentNotFoundException() {
//        long invalidId = 99999;
//        when(repository.existsById(invalidId)).thenReturn(false);
//
//        assertThrows(DepartmentNotFoundException.class, () -> service.deleteById(invalidId));
//
//        verify(repository, times(1)).existsById(invalidId);
//        verify(repository, times(0)).delete(any());
//    }
//}
