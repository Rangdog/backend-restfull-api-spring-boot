package com.fingerprint.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "history_false")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryFalse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String label;
    private Double similarity;
    private Date time;
    private String reason;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "door_id")
    private Door door;
}
