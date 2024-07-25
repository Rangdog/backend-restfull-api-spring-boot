package com.fingerprint.demo.service;

import com.fingerprint.demo.dto.MemberDTO;
import com.fingerprint.demo.model.Member;
import com.fingerprint.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    public Member saveFromDTO (MemberDTO memberDTO, MultipartFile imageFile) throws IOException {
        Member member = MemberMapper.INSTANCE.memberDTOToMember(memberDTO);
        if(memberDTO.getImagePath() != null){
            try{
                File tempFile = File.createTempFile("fingerprint_", "tmp");
                try(FileOutputStream out = new FileOutputStream(tempFile)){
                    out.write(imageFile.getBytes());
                }
                RestTemplate restTemplate = new RestTemplate();
                String uploadUrl = "http://localhost:5000/api/upload";
                HttpHeaders headers = new org.springframework.http.HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                HttpEntity<byte[]> requestEntity = new HttpEntity<>(imageFile.getBytes(), headers);
                ResponseEntity<Map> responseEntity = restTemplate.postForEntity(uploadUrl,requestEntity, Map.class);
                if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null){
                    Integer newLabel = (Integer) responseEntity.getBody().get("new_label");
                    member.setFingerprint(newLabel.longValue());
                    member.setImagePath(tempFile.getAbsolutePath());
                    return memberRepository.save(member);
                }
                else{
                    throw new RuntimeException("Error uploading image: " + responseEntity.getBody());
                }
            }
            catch(IOException e){
                throw new RuntimeException("Error processing image file: " + e.getMessage());
            }
        }
        return memberRepository.save(member);
    }

}
