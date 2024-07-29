package com.fingerprint.demo.service;

import com.fingerprint.demo.dto.DetailVerifyDTO;
import com.fingerprint.demo.dto.MemberDTO;
import com.fingerprint.demo.model.DetailVerify;
import com.fingerprint.demo.repository.DetailVerifyRepository;
import com.fingerprint.demo.repository.DoorRepository;
import com.fingerprint.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetailVerifyService {
    @Autowired
    private DetailVerifyRepository detailVerifyRepository;

    @Autowired
    private DoorRepository doorRepository; // Inject repository cho Door

    @Autowired
    private MemberRepository memberRepository; // Inject repository cho Member
    public List<DetailVerify> findAll(){
        return detailVerifyRepository.findAll();
    }

    public DetailVerify findById(Long id){
        return detailVerifyRepository.findById(id).orElse(null);
    }

    public DetailVerify save(DetailVerify detailVerify){
        return detailVerifyRepository.save(detailVerify);
    }

    public void delete(Long id){
        detailVerifyRepository.deleteById(id);
    }

    public List<DetailVerifyDTO> findAllDTO() {
        List<DetailVerify> detailVerifies = detailVerifyRepository.findAll();
        return DetailVerifyMapper.INSTANCE.detailVerifiesToDetailVerifyDTOs(detailVerifies); // Chuyển đổi sang DTO
    }

    public DetailVerifyDTO findDTOById(Long id) {
        DetailVerify detailVerify = detailVerifyRepository.findById(id).orElse(null);
        return detailVerify != null ? DetailVerifyMapper.INSTANCE.detailVerifyToDetailVerifyDTO(detailVerify) : null; // Chuyển đổi sang DTO
    }

    public DetailVerify updateDetailVerify(Long id, DetailVerifyDTO detailVerifyDTO) {
        DetailVerify existingDetailVerify = detailVerifyRepository.findById(id).orElse(null); // Tìm kiếm DetailVerify theo ID
        if (existingDetailVerify != null) {
            // Cập nhật các thuộc tính từ DetailVerifyDTO
            existingDetailVerify.setDoor(DoorMapper.INSTANCE.doorDTOToDoor(detailVerifyDTO.getDoor())); // Cập nhật Door
            existingDetailVerify.setMember(MemberMapper.INSTANCE.memberDTOToMember(detailVerifyDTO.getMember())); // Cập nhật Member
            existingDetailVerify.setIsEnable(detailVerifyDTO.getIsEnable());
            return detailVerifyRepository.save(existingDetailVerify); // Lưu lại vào cơ sở dữ liệu
        }
        return null; // Trả về null nếu không tìm thấy DetailVerify
    }

    public DetailVerify deleteDetailVerify(Long id, DetailVerifyDTO detailVerifyDTO) {
        DetailVerify existingDetailVerify = detailVerifyRepository.findById(id).orElse(null); // Tìm kiếm DetailVerify theo ID
        if (existingDetailVerify != null) {
            // Cập nhật các thuộc tính từ DetailVerifyDTO
            existingDetailVerify.setDoor(DoorMapper.INSTANCE.doorDTOToDoor(detailVerifyDTO.getDoor())); // Cập nhật Door
            existingDetailVerify.setMember(MemberMapper.INSTANCE.memberDTOToMember(detailVerifyDTO.getMember())); // Cập nhật Member
            existingDetailVerify.setIsEnable(!detailVerifyDTO.getIsEnable());
            return detailVerifyRepository.save(existingDetailVerify); // Lưu lại vào cơ sở dữ liệu
        }
        return null; // Trả về null nếu không tìm thấy DetailVerify
    }

    public DetailVerify saveFromDTO(DetailVerifyDTO detailVerifyDTO) {
        DetailVerify detailVerify = DetailVerifyMapper.INSTANCE.detailVerifyDTOToDetailVerify(detailVerifyDTO); // Chuyển đổi từ DTO sang DetailVerify
        detailVerify.setDoor(DoorMapper.INSTANCE.doorDTOToDoor(detailVerifyDTO.getDoor()));
        detailVerify.setMember(MemberMapper.INSTANCE.memberDTOToMember(detailVerifyDTO.getMember()));
        detailVerify.setIsEnable(true);
        return detailVerifyRepository.save(detailVerify);
    }

    public List<MemberDTO> findMembersByDoorId(Long doorId){
        List<DetailVerify> detailVerifies = detailVerifyRepository.findByDoorId(doorId);
        return detailVerifies.stream().map(DetailVerify::getMember).distinct().map(MemberMapper.INSTANCE::memberToMemberDTO).collect(Collectors.toList());
    }

    public DetailVerifyDTO findByDoorAndMember(Long doorId, Long memberId) {
        DetailVerify detailVerifies = detailVerifyRepository.findByDoorIdAndMemberId(doorId, memberId);
        return DetailVerifyMapper.INSTANCE.detailVerifyToDetailVerifyDTO(detailVerifies);
    }

}
