package com.fingerprint.demo.repository;

import com.fingerprint.demo.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByDetailVerifyDoorId(Long doorId);
    List<History> findByDetailVerifyMemberId(Long memberId);
}
