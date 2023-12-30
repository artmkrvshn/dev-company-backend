//package com.dev.company;
//
//import com.dev.company.dto.response.DepartmentResponse;
//import com.dev.company.dto.response.EmployeeResponse;
//import com.dev.company.entity.Department;
//import com.dev.company.repository.jpa.DepartmentRepository;
//import com.dev.company.service.DepartmentService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.Collections;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class DevCompanyApplicationTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    @Test
//    void contextLoads() {
//
//    }
//
//    @Test
//    void departmentPagesExist() throws Exception {
//        mockMvc.perform(get("/departments")).andExpect(status().isOk());
//        mockMvc.perform(get("/departments/1")).andExpect(status().isOk());
//        mockMvc.perform(get("/departments/edit/1")).andExpect(status().isOk());
//        mockMvc.perform(get("/departments/new")).andExpect(status().isOk());
//        mockMvc.perform(
//                post("/departments/new")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("newDepartment", "")).andExpect(status().isOk());
//        mockMvc.perform(post("/departments/1")).andExpect(status().isOk());
//        mockMvc.perform(post("/departments/1")).andExpect(status().isOk());
//    }
//
//    @Test
//    void canSubmitValidForm() throws Exception {
//
//        DepartmentService department = Mockito.mock(DepartmentService.class);
//
//        Mockito.stub(department.create(new Department())).then();
//
//        Mockito.verify(department).create(new Department());
//        mockMvc.perform(post("/departments/new")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .content(buildUrlEncodedFormEntity(
//                        "name", "kurwa",
//                        "someparam2", "value2"))
//                .content("name=kurwa2")
//        ).andExpect(status().isOk());
//    }
//
//    @BeforeEach
//    void setUp() {
//        cityRepository = Mockito.mock(CityRepository.class);
//        cityService = new CityServiceImpl(cityRepository);
//    }
//
//    @Test
//    void find() throws Exception {
//        DepartmentRepository departmentRepository = Mockito.mock(DepartmentRepository.class);
//        Department expectedDepartment = new Department(1L, "new", Collections.emptyList());
//
//        Mockito.when(departmentRepository.find(expected.getId()))
//                .thenReturn(Optional.of(expected));
//        City actual = cityService.find(expected.getId());
//        ReflectionAssert.assertReflectionEquals(expected, actual);
//    }
//
//    @Test
//    void delete() throws Exception {
//        City expected = createCity();
//        cityService.delete(expected);
//        Mockito.verify(cityRepository).delete(expected);
//    }
//
//    private String buildUrlEncodedFormEntity(String... params) {
//        if ((params.length % 2) > 0) {
//            throw new IllegalArgumentException("Need to give an even number of parameters");
//        }
//        StringBuilder result = new StringBuilder();
//        for (int i = 0; i < params.length; i += 2) {
//            if (i > 0) {
//                result.append('&');
//            }
//            result.
//                    append(URLEncoder.encode(params[i], StandardCharsets.UTF_8)).
//                    append('=').
//                    append(URLEncoder.encode(params[i + 1], StandardCharsets.UTF_8));
//        }
//        return result.toString();
//    }
//
//    @Test
//    void employeePagesExist() throws Exception {
//        mockMvc.perform(get("/employees")).andExpect(status().isOk());
//        mockMvc.perform(get("/employees/1")).andExpect(status().isOk());
//        mockMvc.perform(get("/employees/1/edit")).andExpect(status().isOk());
//        mockMvc.perform(get("/employees/new")).andExpect(status().isOk());
//        mockMvc.perform(post("/employees/new")).andExpect(status().isOk());
//    }
//
//    @Test
//    void positionPagesExist() throws Exception {
//        mockMvc.perform(get("/positions")).andExpect(status().isOk());
//        mockMvc.perform(get("/positions/edit")).andExpect(status().isOk());
//        mockMvc.perform(post("/positions/new")).andExpect(status().isOk());
//    }
//
//    @Test
//    void projectPagesExist() throws Exception {
//        mockMvc.perform(get("/projects")).andExpect(status().isOk());
//        mockMvc.perform(get("/projects/1")).andExpect(status().isOk());
//        mockMvc.perform(get("/projects/1/edit")).andExpect(status().isOk());
//        mockMvc.perform(get("/projects/new")).andExpect(status().isOk());
//        mockMvc.perform(post("/projects/new")).andExpect(status().isOk());
//
//    }
//
//    @Test
//    void statusPagesExist() throws Exception {
//        mockMvc.perform(get("/statuses")).andExpect(status().isOk());
//        mockMvc.perform(get("/statuses/edit")).andExpect(status().isOk());
//        mockMvc.perform(post("/statuses/new")).andExpect(status().isOk());
//        mockMvc.perform(patch("/statuses/1")).andExpect(status().isOk());
//        mockMvc.perform(delete("/statuses/1")).andExpect(status().isOk());
//    }
//}
