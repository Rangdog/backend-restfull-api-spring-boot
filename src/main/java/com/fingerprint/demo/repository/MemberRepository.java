package com.fingerprint.demo.repository;

import com.fingerprint.demo.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository <Member,Long> {
    List<Member> findByDetailVerifiesDoorId(Long doorId);
    @Query("SELECT d.member FROM DetailVerify d WHERE d.door.id = :doorId AND d.isEnable = true")
    List<Member> findByDetailVerifiesDoorIdWithEnableTrue(Long doorId);

    @Query("SELECT m FROM Member m WHERE m.id NOT IN (SELECT d.member.id FROM DetailVerify d WHERE d.door.id = :doorId) OR m.id IN (SELECT d.member.id FROM DetailVerify d WHERE d.door.id = :doorId AND d.isEnable = false)")
    List<Member> findByNotInDetailVerifiesOrDisabled(Long doorId);
}
