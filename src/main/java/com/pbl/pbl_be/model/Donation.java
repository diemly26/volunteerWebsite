package com.pbl.pbl_be.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "donation_id")
    private Integer donationId;


    @Column(name = "amount")
    private Long amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "txn_ref")
    private String txnRef;

    @Column(name = "good_description")
    private String goodDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

  public enum Type{
        money, goods
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}

