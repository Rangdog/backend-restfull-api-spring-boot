package com.fingerprint.demo.service;

import com.fingerprint.demo.model.History;
import com.fingerprint.demo.model.HistoryFalse;
import com.fingerprint.demo.repository.HistoryFalseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryFalseService {
    @Autowired
    private HistoryFalseRepository historyFalseRepository;

    public List<HistoryFalse> findAll(){
        return historyFalseRepository.findAll();
    }
    public HistoryFalse findById(Long id){
        return historyFalseRepository.findById(id).orElse(null);
    }
    public HistoryFalse save(HistoryFalse historyFalse){
        return historyFalseRepository.save(historyFalse);
    }
    public void delete(Long id){
        historyFalseRepository.deleteById(id);
    }
}
