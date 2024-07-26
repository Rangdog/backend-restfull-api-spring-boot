package com.fingerprint.demo.service;

import com.fingerprint.demo.dto.HistoryDTO;
import com.fingerprint.demo.model.History;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {DetailVerifyMapper.class})
public interface HistoryMapper {
    HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);
    @Mapping(source = "detailVerify", target = "detailVerify")
    HistoryDTO historyToHistoryDTO(History history);
    @Mapping(target = "detailVerify", source = "detailVerify")
    History historyDTOToHistory(HistoryDTO historyDTO);
    List<HistoryDTO> historyToHistoryDTOs(List<History> histories);
    List<History> historyDTOToHistories(List<HistoryDTO> historyDTOS);
}
