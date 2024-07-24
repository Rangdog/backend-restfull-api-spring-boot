package com.fingerprint.demo.repository;

import com.fingerprint.demo.model.DetailVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailVerifyRepository extends JpaRepository<DetailVerify, Long> {
}
