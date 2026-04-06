package com.asset.ams.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class TransferHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long thid;

    @ManyToOne(fetch = FetchType.LAZY)
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    private Location fromLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    private Location toLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    private User approvedBy;

    private String reason;

    private LocalDateTime transferredAt;
}
