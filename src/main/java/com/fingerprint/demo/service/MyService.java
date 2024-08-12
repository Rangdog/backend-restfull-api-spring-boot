package com.fingerprint.demo.service;

import com.fingerprint.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    @Autowired
    private DoorRepository doorRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private DetailVerifyRepository detailVerifyRepository;
    @Autowired
    private HistoryFalseRepository historyFalseRepository;

    @Transactional
    public void resetDatabase(){
        historyRepository.deleteAll();
        detailVerifyRepository.deleteAll();
        doorRepository.deleteAll();
        memberRepository.deleteAll();
        historyFalseRepository.deleteAll();
    }
}
