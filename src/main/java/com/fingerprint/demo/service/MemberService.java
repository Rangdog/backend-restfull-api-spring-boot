package com.fingerprint.demo.service;

import com.fingerprint.demo.dto.MemberDTO;
import com.fingerprint.demo.model.Member;
import com.fingerprint.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        if(imageFile != null && !imageFile.isEmpty()){
            try{
//                File tempFile = File.createTempFile("fingerprint_", ".jpg");
//                imageFile.transferTo(tempFile);
//                System.out.println("Temporary file created at: " + tempFile.getAbsolutePath());
                RestTemplate restTemplate = new RestTemplate();
                String uploadUrl = "http://localhost:5000/api/upload";
                // Tạo MultipartBody
                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("file", new ByteArrayResource(imageFile.getBytes()) {
                    @Override
                    public String getFilename() {
                        return imageFile.getOriginalFilename(); // Đặt tên tệp gốc
                    }
                });// Gửi tệp như một FileSystemResource
                // Tạo HttpHeaders
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                // Tạo HttpEntity
                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
                // Gửi yêu cầu POST
                ResponseEntity<Map> responseEntity = restTemplate.postForEntity(uploadUrl, requestEntity, Map.class);
                if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null){
                    Integer newLabel = (Integer) responseEntity.getBody().get("new_label");
                    System.out.println(newLabel);
                    member.setFingerprint(newLabel.longValue());
                    String imagePath = saveImage(newLabel,imageFile);
                    member.setImagePath(imagePath);
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

    private String saveImage(Integer label, MultipartFile file) throws IOException {
        // Tạo đường dẫn để lưu tệp
        String imagePath = "D:\\Project\\backend-restfull-api-spring-boot\\src\\main\\resources\\images\\" + label + ".jpg";
        Path path = Paths.get(imagePath);

        // Kiểm tra và tạo thư mục nếu chưa tồn tại
        Files.createDirectories(path.getParent());

        // Kiểm tra kích thước tệp
        if (file.getSize() == 0) {
            throw new IOException("Image file is empty.");
        }

        // Lưu tệp
        Files.write(path, file.getBytes());

        System.out.println("Image saved at: " + imagePath);
        return imagePath; // Trả về đường dẫn tệp đã lưu
    }
    public List<MemberDTO> findMembersByDoorId(Long doorId) {
        List<Member> members = memberRepository.findByDetailVerifiesDoorId(doorId);
        return MemberMapper.INSTANCE.memberToMemberDTOs(members);
    }

    public List<MemberDTO> findMembersByDoorIdWithEnableTrue(Long doorId) {
        List<Member> members = memberRepository.findByDetailVerifiesDoorIdWithEnableTrue(doorId);
        return MemberMapper.INSTANCE.memberToMemberDTOs(members);
    }

    public List<MemberDTO> findByNotInDetailVerifiesOrDisabled(Long doorId) {
        List<Member> members = memberRepository.findByNotInDetailVerifiesOrDisabled(doorId);
        return MemberMapper.INSTANCE.memberToMemberDTOs(members);
    }
}
