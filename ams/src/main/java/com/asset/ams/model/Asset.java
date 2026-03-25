package com.asset.ams.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@SQLDelete(sql = "UPDATE asset SET deleted = true WHERE asset_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetId;

    private String assetName;

    @Column(unique = true)
    private String serialNumber;

    private String brand;
    private String model;

    private LocalDate purchaseDate;
    private LocalDate warrantyExpiry;

    private BigDecimal cost;

    private String status;     // Available, Assigned, Retired
    @Column(name = "asset_condition")
    private String assetCondition;  // Good, Fair, Poor

    @Column(length = 1000)
    private String notes;

    //  Relationships
    @ManyToOne
    @JoinColumn(name = "type_id")
    private AssetType assetType;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(nullable = false)
    private boolean deleted = false;

    //  Audit Fields
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime createdBy;
    private String updatedBy;
    private String updatedAt;
}
