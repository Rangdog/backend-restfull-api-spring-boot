package com.fingerprint.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "door")
public class Door {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "door_name")
    private String doorName;

    private String password;

    private String location;


    @OneToMany(mappedBy = "door", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetailVerify> detailVerifies;
}
