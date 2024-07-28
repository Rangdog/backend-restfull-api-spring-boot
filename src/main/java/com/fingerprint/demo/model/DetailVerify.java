package com.fingerprint.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detail_verify", uniqueConstraints = {@UniqueConstraint(columnNames = {"door_id", "member_id"})})
public class DetailVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "door_id", nullable = true)
    private Door door;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    @OneToMany(mappedBy = "detailVerify", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<History> histories;

    @Column(name = "enable")
    private Boolean isEnable;
}
