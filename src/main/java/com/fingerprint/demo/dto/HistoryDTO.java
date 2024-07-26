package com.fingerprint.demo.dto;

import com.fingerprint.demo.model.DetailVerify;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {
    private Long id;
    private DetailVerifyDTO  detailVerify;
    private Date Time;
}
