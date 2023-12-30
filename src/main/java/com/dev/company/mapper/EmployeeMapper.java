package com.dev.company.mapper;

import com.dev.company.dto.EmployeeDTO;
import com.dev.company.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface EmployeeMapper {

    EmployeeDTO map(Employee employee);

    List<EmployeeDTO> map(List<Employee> employees);

}