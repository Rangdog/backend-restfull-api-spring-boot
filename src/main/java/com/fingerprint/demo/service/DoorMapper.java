package com.fingerprint.demo.service;

import com.fingerprint.demo.dto.DoorDTO;
import com.fingerprint.demo.model.Door;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DoorMapper {
    DoorMapper INSTANCE = Mappers.getMapper(DoorMapper.class);
    DoorDTO doorToDoorDTO(Door door);
    Door doorDTOToDoor(DoorDTO doorDTO);
    List<DoorDTO> doorsToDoorDTOs(List<Door> doors);
    List<Door> doorDTOsToDoors(List<DoorDTO> doorDTOs);
}
