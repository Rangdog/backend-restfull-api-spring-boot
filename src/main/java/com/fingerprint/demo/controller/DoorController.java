package com.fingerprint.demo.controller;

import com.fingerprint.demo.dto.DetailVerifyDTO;
import com.fingerprint.demo.dto.DoorDTO;
import com.fingerprint.demo.dto.HistoryDTO;
import com.fingerprint.demo.dto.MemberDTO;
import com.fingerprint.demo.model.DetailVerify;
import com.fingerprint.demo.model.Door;
import com.fingerprint.demo.model.History;
import com.fingerprint.demo.model.Member;
import com.fingerprint.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/door")
public class DoorController {
    @Autowired
    private DoorService doorService;

    @Autowired
    private MemberService memberService;
    @Autowired
    private DetailVerifyService detailVerifyService;
    @Autowired
    private HistorySevice historySevice;
    @GetMapping
    public List<DoorDTO> getAllDoor(){
        return doorService.findAllDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoorDTO> getDoorById(@PathVariable Long id){
        DoorDTO doorDTO = doorService.findDTOById(id);
        return doorDTO != null ? ResponseEntity.ok(doorDTO) : ResponseEntity.notFound().build();
    }
    @GetMapping("/{doorId}/members")
    public List<MemberDTO> getMembersByDoorId(@PathVariable Long doorId) {
        return memberService.findMembersByDoorIdWithEnableTrue(doorId);
    }
    @GetMapping("/{doorId}/history")
    public List<HistoryDTO> getHistoryByDoorId(@PathVariable Long doorId) {
        return historySevice.findHistoryByDoorId(doorId);
    }
    @PostMapping
    public ResponseEntity<DoorDTO> createDoor(@RequestBody DoorDTO doorDTO){
        System.out.println(doorDTO);System.out.println("Received DoorDTO: " + doorDTO);
        Door createdDoor = doorService.saveFromDTO(doorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(DoorMapper.INSTANCE.doorToDoorDTO(createdDoor));
    }

    @PostMapping("/{doorId}/verify")
    public ResponseEntity<?> verifyFingerprint(@PathVariable Long doorId, @RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("Vui lòng chọn file để upload");
        }
        try{
            Door door = doorService.findById(doorId);
            if (door == null) {
                return ResponseEntity.notFound().build();
            }
            RestTemplate restTemplate = new RestTemplate();
            String verifyUrl = "http://localhost:5000/api/verify";
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });
            body.add("doorId", doorId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(verifyUrl, requestEntity, Map.class);
            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                Map<String, Object> flaskResponse = responseEntity.getBody();
                if (flaskResponse.containsKey("message") && flaskResponse.get("message").equals("Unlock successful!")) {
                    Long bestMatchLabel = ((Number) flaskResponse.get("label")).longValue();
                    // Lấy tất cả các member của door
                    List<MemberDTO> doorMembers = memberService.findMembersByDoorId(doorId);
                    MemberDTO matchedMember = doorMembers.stream()
                            .filter(member -> Optional.ofNullable(member.getFingerprint()).map(fingerprint -> fingerprint.equals(bestMatchLabel)).orElse(false))
                            .findFirst()
                            .orElse(null);
                    System.out.println(bestMatchLabel);
                    if (matchedMember != null) {
                        // Tạo history
                        DetailVerifyDTO detailVerifyDTO = detailVerifyService.findByDoorAndMember(door.getId(), matchedMember.getId());
                        History history = new History();
                        history.setDetailVerify(DetailVerifyMapper.INSTANCE.detailVerifyDTOToDetailVerify(detailVerifyDTO));
                        LocalDateTime now = LocalDateTime.now();
                        Timestamp timestamp = Timestamp.valueOf(now);
                        history.setTime(timestamp);
                        historySevice.save(history);
                        // Cập nhật thời gian mở cửa gần nhất
                        return ResponseEntity.ok(Collections.singletonMap("message", "Mở cửa thành công! Xin chào " + matchedMember.getName()));
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Collections.singletonMap("message", "Bạn không có quyền mở cửa này."));
                    }
                }
                else{
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Collections.singletonMap("message", "Dấu vân tay của bạn chưa có trong cơ sở dữ liệu."));
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("message", "Lỗi khi xác thực vân tay"));
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi không xác định: " + e.getMessage());
        }
    }

    @PostMapping("/{doorId}/verify2")
    public ResponseEntity<?> verifyFingerprintByModel2(@PathVariable Long doorId, @RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("Vui lòng chọn file để upload");
        }
        try{
            Door door = doorService.findById(doorId);
            if (door == null) {
                return ResponseEntity.notFound().build();
            }
            RestTemplate restTemplate = new RestTemplate();
            String verifyUrl = "http://localhost:5000/api/verify_model_2";
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });
            body.add("doorId", doorId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(verifyUrl, requestEntity, Map.class);
            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                Map<String, Object> flaskResponse = responseEntity.getBody();
                if (flaskResponse.containsKey("message") && flaskResponse.get("message").equals("Unlock successful!")) {
                    Long bestMatchLabel = ((Number) flaskResponse.get("label")).longValue();
                    // Lấy tất cả các member của door
                    List<MemberDTO> doorMembers = memberService.findMembersByDoorId(doorId);
                    MemberDTO matchedMember = doorMembers.stream()
                            .filter(member -> Optional.ofNullable(member.getFingerprint()).map(fingerprint -> fingerprint.equals(bestMatchLabel)).orElse(false))
                            .findFirst()
                            .orElse(null);
                    System.out.println(bestMatchLabel);
                    if (matchedMember != null) {
                        // Tạo history
                        DetailVerifyDTO detailVerifyDTO = detailVerifyService.findByDoorAndMember(door.getId(), matchedMember.getId());
                        History history = new History();
                        history.setDetailVerify(DetailVerifyMapper.INSTANCE.detailVerifyDTOToDetailVerify(detailVerifyDTO));
                        LocalDateTime now = LocalDateTime.now();
                        Timestamp timestamp = Timestamp.valueOf(now);
                        history.setTime(timestamp);
                        historySevice.save(history);
                        // Cập nhật thời gian mở cửa gần nhất
                        return ResponseEntity.ok(Collections.singletonMap("message", "Mở cửa thành công! Xin chào " + matchedMember.getName()));
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Collections.singletonMap("message", "Bạn không có quyền mở cửa này."));
                    }
                }
                else{
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Collections.singletonMap("message", "Dấu vân tay của bạn chưa có trong cơ sở dữ liệu."));
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("message", "Lỗi khi xác thực vân tay"));
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi không xác định: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoorDTO> updateDoor(@PathVariable Long id, @RequestBody DoorDTO doorDTO){
        Door updatedDoor = doorService.updateDoor(id,doorDTO);
        return updatedDoor != null ? ResponseEntity.ok(DoorMapper.INSTANCE.doorToDoorDTO(updatedDoor)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoor(@PathVariable Long id){
        doorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
