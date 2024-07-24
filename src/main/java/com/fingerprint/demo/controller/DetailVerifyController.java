package com.fingerprint.demo.controller;

import com.fingerprint.demo.dto.DetailVerifyDTO;
import com.fingerprint.demo.model.DetailVerify;
import com.fingerprint.demo.service.DetailVerifyMapper;
import com.fingerprint.demo.service.DetailVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detail-verify")
public class DetailVerifyController {
    @Autowired
    private DetailVerifyService detailVerifyService;

    @GetMapping
    public List<DetailVerifyDTO> getAllDetailVerify(){
        return detailVerifyService.findAllDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailVerifyDTO> getDetailVerifyById(@PathVariable Long id){
        DetailVerifyDTO detailVerifyDTO = detailVerifyService.findDTOById(id);
        return detailVerifyDTO!=null ? ResponseEntity.ok(detailVerifyDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public  ResponseEntity<DetailVerifyDTO> createDetailVerify(@RequestBody DetailVerifyDTO detailVerifyDTO){
        DetailVerify createdDetailVerify = detailVerifyService.saveFromDTO(detailVerifyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(DetailVerifyMapper.INSTANCE.detailVerifyToDetailVerifyDTO(createdDetailVerify));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetailVerifyDTO> updateDetailVerify(@PathVariable Long id, @RequestBody DetailVerifyDTO detailVerifyDTO) {
        DetailVerify updatedDetailVerify = detailVerifyService.updateDetailVerify(id, detailVerifyDTO); // Cập nhật DetailVerify
        return updatedDetailVerify != null
                ? ResponseEntity.ok(DetailVerifyMapper.INSTANCE.detailVerifyToDetailVerifyDTO(updatedDetailVerify))
                : ResponseEntity.notFound().build(); // Trả về DetailVerifyDTO hoặc 404
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetailVerify(@PathVariable Long id){
        detailVerifyService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
