package com.fingerprint.demo.service;

import com.fingerprint.demo.dto.MemberDTO;
import com.fingerprint.demo.model.Member;
import com.fingerprint.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Member findById(Long id){
        return memberRepository.findById(id).orElse(null);
    }

    public Member save(Member member){
        return memberRepository.save(member);
    }
    public void delete(Long id){
        memberRepository.deleteById(id);
    }

    public List<MemberDTO> findAllDTO(){
        List<Member> members = memberRepository.findAll();
        return MemberMapper.INSTANCE.memberToMemberDTOs(members);
    }

    public MemberDTO findDTOById(Long id){
        Member member = memberRepository.findById(id).orElse(null);
        return member != null ? MemberMapper.INSTANCE.memberToMemberDTO(member) : null;
    }

    public Member updateMemBer(Long id, MemberDTO memberDTO){
        Member existingMember = memberRepository.findById(id).orElse(null);
        if(existingMember != null){
            existingMember.setFingerprint(memberDTO.getFingerprint());
            existingMember.setName(memberDTO.getName());
            return memberRepository.save(existingMember);
        }
        return null;
    }

    public Member saveFromDTO (MemberDTO memberDTO){
        Member member = MemberMapper.INSTANCE.memberDTOToMember(memberDTO);
        return memberRepository.save(member);
    }

}
