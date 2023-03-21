package com.deploy.Travalue.domain.user;

import com.deploy.Travalue.domain.common.AuditingTimeEntity;
import com.deploy.Travalue.domain.travel.Category;
import com.deploy.Travalue.domain.travel.Travel;
import com.deploy.Travalue.domain.travel.VisitedTravel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String nickname;

    @Column(length = 2083)
    private String profileImage;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "INTEGER default 0")
    private int travelCount;

    @Embedded
    private SocialInformation socialInformation;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Category> categoryList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Travel> travelList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<VisitedTravel> visitedTravelList = new ArrayList<>();
}
