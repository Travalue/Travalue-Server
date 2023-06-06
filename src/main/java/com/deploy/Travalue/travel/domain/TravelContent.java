package com.deploy.Travalue.travel.domain;

import com.deploy.Travalue.common.domain.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false)
    private Integer pinIndex;

    private TravelContent(Travel travel, String content, String imageUrl, Integer pinIndex) {
        this.travel = travel;
        this.content = content;
        this.imageUrl = imageUrl;
        this.pinIndex = pinIndex;
    }

    public static TravelContent newInstance(Travel travel, String content, String imageUrl, Integer order) {
        return new TravelContent(travel, content, imageUrl, order);
    }
}
