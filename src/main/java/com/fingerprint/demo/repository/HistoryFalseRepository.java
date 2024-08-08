package com.fingerprint.demo.repository;

import com.fingerprint.demo.model.HistoryFalse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryFalseRepository extends JpaRepository<HistoryFalse, Long> {
}
