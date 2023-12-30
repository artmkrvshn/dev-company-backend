package com.dev.company.mapper;

import com.dev.company.dto.ProjectDTO;
import com.dev.company.dto.ProjectDetailedDTO;
import com.dev.company.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface ProjectMapper {

    ProjectDetailedDTO map(Project project);

    List<ProjectDTO> map(List<Project> projects);

}
