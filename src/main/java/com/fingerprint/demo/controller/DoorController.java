package com.fingerprint.demo.controller;

import com.fingerprint.demo.dto.DoorDTO;
import com.fingerprint.demo.model.Door;
import com.fingerprint.demo.service.DoorMapper;
import com.fingerprint.demo.service.DoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/door")
public class DoorController {
    @Autowired
    private DoorService doorService;

    @GetMapping
    public List<DoorDTO> getAllDoor(){
        return doorService.findAllDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoorDTO> getDoorById(@PathVariable Long id){
        DoorDTO doorDTO = doorService.findDTOById(id);
        return doorDTO != null ? ResponseEntity.ok(doorDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DoorDTO> createDoor(@RequestBody DoorDTO doorDTO){
        System.out.println(doorDTO);System.out.println("Received DoorDTO: " + doorDTO);
        Door createdDoor = doorService.saveFromDTO(doorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(DoorMapper.INSTANCE.doorToDoorDTO(createdDoor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoorDTO> updateDoor(@PathVariable Long id, @RequestBody DoorDTO doorDTO){
        Door updatedDoor = doorService.updateDoor(id,doorDTO);
        return updatedDoor != null ? ResponseEntity.ok(DoorMapper.INSTANCE.doorToDoorDTO(updatedDoor)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoor(@PathVariable Long id){
        doorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
