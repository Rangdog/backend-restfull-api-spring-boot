package com.fingerprint.demo.repository;

import com.fingerprint.demo.model.Door;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoorRepository extends JpaRepository<Door,Long> {
}
