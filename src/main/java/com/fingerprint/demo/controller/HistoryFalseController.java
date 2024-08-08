package com.fingerprint.demo.controller;

import com.fingerprint.demo.model.HistoryFalse;
import com.fingerprint.demo.service.HistoryFalseService;
import com.fingerprint.demo.service.HistorySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/historyfalse")
public class HistoryFalseController {
    @Autowired
    private HistoryFalseService historyFalseService;
    @GetMapping
    public List<HistoryFalse>  getAllHistoriesFalse(){
        return historyFalseService.findAll();
    }
}
