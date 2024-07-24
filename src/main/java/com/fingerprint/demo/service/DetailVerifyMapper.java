package com.fingerprint.demo.service;

import com.fingerprint.demo.dto.DetailVerifyDTO;
import com.fingerprint.demo.model.DetailVerify;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DetailVerifyMapper {
    DetailVerifyMapper INSTANCE = Mappers.getMapper(DetailVerifyMapper.class);
    DetailVerifyDTO detailVerifyToDetailVerifyDTO(DetailVerify detailVerify);
    DetailVerify detailVerifyDTOToDetailVerify(DetailVerifyDTO detailVerifyDTO);
    List<DetailVerifyDTO> detailVerifiesToDetailVerifyDTOs(List<DetailVerify> detailVerifies);
    List<DetailVerify> detailVerifyDTOsToDetailVerifies(List<DetailVerifyDTO> detailVerifyDTOs);
}
