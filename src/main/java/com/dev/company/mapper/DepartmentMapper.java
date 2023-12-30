package com.dev.company.mapper;

import com.dev.company.dto.DepartmentDTO;
import com.dev.company.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface DepartmentMapper {

    DepartmentDTO map(Department department);

    List<DepartmentDTO> map(List<Department> departments);

}
