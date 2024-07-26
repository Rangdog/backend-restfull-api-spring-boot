package com.fingerprint.demo.repository;

import com.fingerprint.demo.model.DetailVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailVerifyRepository extends JpaRepository<DetailVerify, Long> {
    List<DetailVerify>  findByDoorId(Long doorID);
}
