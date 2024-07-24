package com.fingerprint.demo.service;

import com.fingerprint.demo.dto.HistoryDTO;
import com.fingerprint.demo.model.DetailVerify;
import com.fingerprint.demo.model.History;
import com.fingerprint.demo.repository.DetailVerifyRepository;
import com.fingerprint.demo.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorySevice {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private DetailVerifyRepository detailVerifyRepository;
    public List<History> findAll(){
        return historyRepository.findAll();
    }

    public History findById(Long id){
        return historyRepository.findById(id).orElse(null);
    }

    public History save(History history){
        return historyRepository.save(history);
    }

    public void delete(Long id){
        historyRepository.deleteById(id);
    }
    public List<HistoryDTO> findAllDTO(){
        List<History> histories = historyRepository.findAll();
        return HistoryMapper.INSTANCE.historyToHistoryDTOs(histories);
    }
    public HistoryDTO findDTOById(Long id){
        History history = historyRepository.findById(id).orElse(null);
        return history != null ? HistoryMapper.INSTANCE.historyToHistoryDTO(history) : null;
    }

    public History updateHistory(Long id, HistoryDTO historyDTO){
        History existingHistory = historyRepository.findById(id).orElse(null);
        if(existingHistory != null){
            existingHistory.setTime(historyDTO.getTime());
            DetailVerify detailVerify = detailVerifyRepository.findById(historyDTO.getDetailVerifyId()).orElse(null);
            existingHistory.setDetailVerify(detailVerify);

            return historyRepository.save(existingHistory);
        }
        return null;
    }

    public History saveFromDTO(HistoryDTO historyDTO){
        History history = HistoryMapper.INSTANCE.historyDTOToHistory(historyDTO);
        return historyRepository.save(history);
    }
}
