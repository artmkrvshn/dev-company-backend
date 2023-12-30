package com.dev.company.mapper;

import com.dev.company.dto.PositionDTO;
import com.dev.company.entity.Position;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface PositionMapper {

    PositionDTO map(Position position);

    List<PositionDTO> map(List<Position> positions);

}
