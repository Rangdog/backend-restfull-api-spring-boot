package com.fingerprint.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {
    private Long id;
    private Long detailVerifyId;
    private Date Time;
}
