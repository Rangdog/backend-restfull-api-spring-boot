package com.fingerprint.demo.service;

import com.fingerprint.demo.dto.HistoryDTO;
import com.fingerprint.demo.model.History;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface HistoryMapper {
    HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);
    HistoryDTO historyToHistoryDTO(History history);
    History historyDTOToHistory(HistoryDTO historyDTO);
    List<HistoryDTO> historyToHistoryDTOs(List<History> histories);
    List<History> historyDTOToHistories(List<HistoryDTO> historyDTOS);
}
