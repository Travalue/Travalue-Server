package com.deploy.Travalue.user.domain;

import com.deploy.Travalue.common.domain.AuditingTimeEntity;
import com.deploy.Travalue.user.dto.CreateUserDto;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String nickname;

    @Column(length = 2083)
    private String profileImage;

    @Column()
    @ColumnDefault("TEXT")
    private String description;

    @Column()
    @ColumnDefault("0")
    private int travelCount;

    @Embedded
    private SocialInformation socialInformation;


    @Builder
    public User(Long id, String description, int travelCount, CreateUserDto createUserDto) {
        SocialInformation socialInformation = SocialInformation.builder()
            .socialId(createUserDto.getSocialId())
            .socialType(createUserDto.getSocialType())
            .build();
        this.id = id;
        this.email = createUserDto.getEmail();
        this.nickname = createUserDto.getNickname();
        this.profileImage = createUserDto.getProfileImage();
        this.description = description;
        this.travelCount = travelCount;
        this.socialInformation = socialInformation;
    }
}
