package com.fingerprint.demo.repository;

import com.fingerprint.demo.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository <Member,Long> {
    List<Member> findByDetailVerifiesDoorId(Long doorId);
}
