package com.fingerprint.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVerifyDTO {
    private Long id;
    private Long doorId;
    private Long memberId;
}
