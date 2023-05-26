package com.deploy.Travalue.user.domain;

import com.deploy.Travalue.user.dto.CreateUserDto;
import javax.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.deploy.Travalue.common.domain.AuditingTimeEntity;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.ColumnDefault;
import lombok.Builder;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(length = 50)
    private String nickname;

    @Column()
    @ColumnDefault("false")
    private boolean isSignupCompleted; //TODO: 닉네임 null로 체크하기!!

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

    public void updateNickname(String nickname) {
        this.nickname = nickname;
        this.isSignupCompleted = true;
    }
}
