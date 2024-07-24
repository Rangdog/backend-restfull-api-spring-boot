package com.fingerprint.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoorDTO {
    private Long id;
    private String doorName;
    private String location;
    private String password;
}
