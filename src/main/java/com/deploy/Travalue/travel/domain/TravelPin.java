package com.deploy.Travalue.travel.domain;

import com.deploy.Travalue.common.domain.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TravelPin extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Travel travel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_information_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TravelInformation travelInformation;

    @Column(nullable = false)
    private Integer pinIndex;

    private TravelPin(Travel travel, TravelInformation travelInformation, Integer pinIndex) {
        this.travel = travel;
        this.travelInformation = travelInformation;
        this.pinIndex = pinIndex;
    }

    public static TravelPin newInstance(Travel travel, TravelInformation travelInformation, Integer pinIndex) {
        return new TravelPin(travel, travelInformation, pinIndex);
    }
}
