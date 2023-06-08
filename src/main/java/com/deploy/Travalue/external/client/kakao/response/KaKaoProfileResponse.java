package com.deploy.Travalue.external.client.kakao.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KaKaoProfileResponse {

    private String id;
    private Properties properties;
    private KakaoAccount kakaoAccount;

    @Getter
    @ToString
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) //여기에도 써줘야됨!
    public class Properties{
        private String nickname;
        private String profileImage;
    }

    @Getter
    @ToString
    public class KakaoAccount{
        private String email;
    }

    public KaKaoProfileResponse(String id, String nickname, String email, String profileImage) {
        this.id = id;
        this.properties.nickname = nickname;
        this.kakaoAccount.email = email;
        this.properties.profileImage = profileImage;
    }

}
