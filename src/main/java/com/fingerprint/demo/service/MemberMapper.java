package com.fingerprint.demo.service;

import com.fingerprint.demo.dto.MemberDTO;
import com.fingerprint.demo.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberDTO memberToMemberDTO(Member member);
    Member memberDTOToMember(MemberDTO memberDTO);
    List<MemberDTO> memberToMemberDTOs(List<Member> members);
    List<Member> memberDTOsToMembers(List<MemberDTO> memberDTOs);
}
