package com.fingerprint.demo.service;

import com.fingerprint.demo.dto.DoorDTO;
import com.fingerprint.demo.model.Door;
import com.fingerprint.demo.repository.DoorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoorService {
    @Autowired
    private DoorRepository doorRepository;

    public List<Door> findAll() {
        return doorRepository.findAll();
    }

    public Door findById(Long id){
        return doorRepository.findById(id).orElse(null);
    }

    public Door save(Door door){
        return doorRepository.save(door);
    }

    public void delete(Long id){
        doorRepository.deleteById(id);
    }

    public List<DoorDTO> findAllDTO(){
        List<Door> doors = doorRepository.findAll();
        return DoorMapper.INSTANCE.doorsToDoorDTOs(doors);
    }

    public DoorDTO findDTOById (Long id){
        Door door = findById(id);
        return door!=null?DoorMapper.INSTANCE.doorToDoorDTO(door) : null;
    }

    public Door saveFromDTO(DoorDTO doorDTO){
        Door door = DoorMapper.INSTANCE.doorDTOToDoor(doorDTO);
        return save(door);
    }

    public Door updateDoor(Long id, DoorDTO doorDTO){
        Door existingDoor = doorRepository.findById(id).orElse(null);
        if(existingDoor != null){
            existingDoor.setDoorName(doorDTO.getDoorName());
            existingDoor.setLocation(doorDTO.getLocation());
            existingDoor.setPassword(doorDTO.getLocation());
            return doorRepository.save(existingDoor);
        }
        return null;
    }
}
