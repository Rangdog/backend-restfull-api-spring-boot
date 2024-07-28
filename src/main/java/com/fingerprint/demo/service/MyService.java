package com.fingerprint.demo.service;

import com.fingerprint.demo.repository.DetailVerifyRepository;
import com.fingerprint.demo.repository.DoorRepository;
import com.fingerprint.demo.repository.HistoryRepository;
import com.fingerprint.demo.repository.MemberRepository;
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

    @Transactional
    public void resetDatabase(){
        historyRepository.deleteAll();
        detailVerifyRepository.deleteAll();
        doorRepository.deleteAll();
        memberRepository.deleteAll();
    }
}
