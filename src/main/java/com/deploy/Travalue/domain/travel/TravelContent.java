package com.deploy.Travalue.domain.travel;

import com.deploy.Travalue.domain.common.AuditingTimeEntity;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TravelContent extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="travel_id")
    private Travel travel;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String text;


}
