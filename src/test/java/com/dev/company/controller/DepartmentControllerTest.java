package com.dev.company.controller;

import com.dev.company.entity.Department;
import com.dev.company.exception.department.DepartmentNotFoundException;
import com.dev.company.mapper.DepartmentMapper;
import com.dev.company.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService service;

    @Autowired
    private DepartmentMapper mapper;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    void contextLoads() {
        Assertions.assertThat(service).isNotNull();
        Assertions.assertThat(mapper).isNotNull();
    }

    @Test
    void givenDepartments_whenGetDepartments_thenReturnDepartments() throws Exception {
        List<Department> departments = List.of(
                createDepartment("Department A"),
                createDepartment("Department B"),
                createDepartment("Department C")
        );

        int size = departments.size();

        when(service.readAll(any(Sort.class))).thenReturn(departments);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/departments"));

        resultActions.andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.length()", is(size))
        );

        for (int i = 0; i < size; i++) {
            resultActions.andExpectAll(
                    jsonPath("$[" + i + "].id", is(departments.get(i).getId().toString())),
                    jsonPath("$[" + i + "].name", is(departments.get(i).getName()))
            );
        }

        verify(service).readAll(any(Sort.class));
    }

    @Test
    void givenValidIdDepartment_whenGetDepartment_thenReturnDepartment() throws Exception {
        Department department = createDepartment("Department A");
        UUID validId = department.getId();

        when(service.readById(validId)).thenReturn(department);

        mockMvc.perform(get("/api/v1/departments/{id}", validId)).andExpectAll(
                status().isOk(),
                content().contentType("application/json"),
                jsonPath("$.id", is(department.getId().toString())),
                jsonPath("$.name", is(department.getName()))
        );

        verify(service).readById(validId);
    }

    @Test
    void givenInvalidId_whenGetDepartment_thenThrowException() throws Exception {
        UUID invalidId = UUID.randomUUID();

        when(service.readById(invalidId)).thenThrow(DepartmentNotFoundException.class);

        mockMvc.perform(get("/api/v1/departments/{id}", invalidId))
                .andExpect(status().isNotFound());

        verify(service).readById(invalidId);
    }

    @Test
    void givenValidDepartmentName_whenPostNew_thenReturnNew() throws Exception {
        String validName = "New Department";
        DepartmentRequest request = DepartmentRequest.builder()
                .name(validName)
                .build();

        Department department = mapper.map(request);

        when(service.create(department)).thenReturn(department);

        String json = jsonMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/v1/departments/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(service).create(department);
    }

    @Test
    void givenInvalidDepartmentName_whenPostNew_thenThrowException() throws Exception {
        String invalidName = "";

        mockMvc.perform(post("/api/v1/departments/new")
                        .param("name", invalidName))
                .andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    void givenInvalidId_whenGetEdit_thenThrowException() {
        Department invalid = new Department();

//        Mockito.when(service.readAllOrderById()).thenReturn(departments);

//        mockMvc.perform(get("/departments")).andExpectAll(
//                status().isOk(),
//                view().name("department/show-all"),
//                model().attribute("department", equalTo(department))
//        );

        verify(service).readAll();
    }

    @Test
    void givenValidDepartment_whenPostEdit_thenUpdate() {
    }

    @Test
    void givenInvalidDepartment_whenPostEdit_thenThrowException() {
    }

    @Test
    void givenValidId_whenPostDelete_thenDelete() throws Exception {
        UUID validId = UUID.randomUUID();

        mockMvc.perform(post("/api/v1/departments/delete/{id}", validId))
                .andExpect(status().isOk());

        verify(service).deleteById(validId);
    }

    @Test
    void givenInvalidId_whenPostDelete_thenThrowException() throws Exception {
        long invalidId = Long.MAX_VALUE;

        mockMvc.perform(post("/departments/delete/{id}", invalidId))
                .andExpect(status().isNotFound());

        verifyNoInteractions(service);
    }

    private Department createDepartment(UUID id, String name) {
        return new Department(id, name);
    }

    private Department createDepartment(String name) {
        return createDepartment(UUID.randomUUID(), name);
    }
}
