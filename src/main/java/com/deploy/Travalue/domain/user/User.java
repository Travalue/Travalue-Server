package com.deploy.Travalue.domain.user;

import com.deploy.Travalue.domain.common.AuditingTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
