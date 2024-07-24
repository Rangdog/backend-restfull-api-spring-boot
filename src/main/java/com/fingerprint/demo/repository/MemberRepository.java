package com.fingerprint.demo.repository;

import com.fingerprint.demo.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository <Member,Long> {
}
