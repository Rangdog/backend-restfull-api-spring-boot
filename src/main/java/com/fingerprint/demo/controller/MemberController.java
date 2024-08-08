package com.fingerprint.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fingerprint.demo.dto.MemberDTO;
import com.fingerprint.demo.model.Member;
import com.fingerprint.demo.service.MemberMapper;
import com.fingerprint.demo.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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


    @GetMapping("/underperformed")
    public List<MemberDTO> getAllMemberCanRegisterDoor(@RequestParam("doorId") Long id){
        return memberService.findByNotInDetailVerifiesOrDisabled(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id){
        MemberDTO memberDTO = memberService.findDTOById(id);
        return memberDTO != null ? ResponseEntity.ok(memberDTO) :ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<MemberDTO> createMember(@RequestParam("file") MultipartFile file,@RequestParam("name") String name){
        try{
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setName(name);
            Member createdMember = memberService.saveFromDTO(memberDTO, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(MemberMapper.INSTANCE.memberToMemberDTO(createdMember));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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

