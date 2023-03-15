package com.deploy.Travalue.domain.travel;

import com.deploy.Travalue.domain.common.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TravelPin extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "pin_travel", joinColumns = @JoinColumn(name = "travel_pin_id"), inverseJoinColumns = @JoinColumn(name = "travel_id"))
    private final List<Travel> travelList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_information_id")
    private TravelInformation travelInformation;
}