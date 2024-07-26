package com.fingerprint.demo.controller;

import com.fingerprint.demo.dto.HistoryDTO;
import com.fingerprint.demo.model.History;
import com.fingerprint.demo.service.HistoryMapper;
import com.fingerprint.demo.service.HistorySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    @Autowired
    private HistorySevice historySevice;

    @GetMapping
    public List<HistoryDTO>  getAllHistories(){
        return historySevice.findAllDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoryDTO> getHistoryById(@PathVariable Long id){
        HistoryDTO historyDTO = historySevice.findDTOById(id);
        return historyDTO != null ? ResponseEntity.ok(historyDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping("/door/{doorId}")
    public ResponseEntity<List<HistoryDTO>> getHistoryByDoorId(@PathVariable Long doorId){
        List<HistoryDTO> historyDTOS = historySevice.findHistoryByDoorId(doorId);
        return ResponseEntity.ok(historyDTOS);
    }

    @PostMapping
    public ResponseEntity<HistoryDTO> createHistory(@RequestBody HistoryDTO historyDTO){
        History craetedHistory = historySevice.saveFromDTO(historyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(HistoryMapper.INSTANCE.historyToHistoryDTO(craetedHistory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoryDTO> updateHistory(@PathVariable Long id, @RequestBody HistoryDTO historyDTO){
        History updatedHistory = historySevice.updateHistory(id,historyDTO);
        return updatedHistory != null ? ResponseEntity.ok(HistoryMapper.INSTANCE.historyToHistoryDTO(updatedHistory)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deteteHistory(@PathVariable Long id){
        historySevice.delete(id);
        return ResponseEntity.noContent().build();
    }
}
