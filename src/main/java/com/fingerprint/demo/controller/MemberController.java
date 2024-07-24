package com.fingerprint.demo.controller;

import com.fingerprint.demo.dto.MemberDTO;
import com.fingerprint.demo.model.Member;
import com.fingerprint.demo.service.MemberMapper;
import com.fingerprint.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<MemberDTO> getAllMember(){
        return memberService.findAllDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id){
        MemberDTO memberDTO = memberService.findDTOById(id);
        return memberDTO != null ? ResponseEntity.ok(memberDTO) :ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@RequestBody MemberDTO memberDTO){
        Member createMember = memberService.saveFromDTO(memberDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberMapper.INSTANCE.memberToMemberDTO(createMember));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember (@PathVariable Long id, @RequestBody MemberDTO memberDTO){
        Member updatedMember = memberService.updateMemBer(id,memberDTO);
        return updatedMember != null ? ResponseEntity.ok(MemberMapper.INSTANCE.memberToMemberDTO(updatedMember)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id){
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
