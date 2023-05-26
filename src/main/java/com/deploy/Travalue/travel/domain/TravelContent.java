package com.deploy.Travalue.travel.domain;

import com.deploy.Travalue.common.domain.AuditingTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class TravelContent extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", nullable = false, foreignKey =  @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Travel travel;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String imageUrl;
}
