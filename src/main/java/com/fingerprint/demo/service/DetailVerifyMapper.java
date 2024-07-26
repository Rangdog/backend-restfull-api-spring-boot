package com.fingerprint.demo.service;

import com.fingerprint.demo.dto.DetailVerifyDTO;
import com.fingerprint.demo.model.DetailVerify;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {DoorMapper.class, MemberMapper.class})
public interface DetailVerifyMapper {
    DetailVerifyMapper INSTANCE = Mappers.getMapper(DetailVerifyMapper.class);
    @Mapping(source = "door", target = "door")
    @Mapping(source = "member", target = "member")
    DetailVerifyDTO detailVerifyToDetailVerifyDTO(DetailVerify detailVerify);
    @Mapping(source = "door", target = "door")
    @Mapping(source = "member", target = "member")
    DetailVerify detailVerifyDTOToDetailVerify(DetailVerifyDTO detailVerifyDTO);
    List<DetailVerifyDTO> detailVerifiesToDetailVerifyDTOs(List<DetailVerify> detailVerifies);
    List<DetailVerify> detailVerifyDTOsToDetailVerifies(List<DetailVerifyDTO> detailVerifyDTOs);
}
