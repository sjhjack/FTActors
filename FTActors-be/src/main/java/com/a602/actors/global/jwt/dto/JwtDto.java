package com.a602.actors.global.jwt.dto;

import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;

public class JwtDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthRequest {
        @Setter
        private String loginId;
        private String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(loginId, password);
        }
    }
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JwtToken{
        private String loginId;
        private String password;
        private String jwt_accessToken;
        private String jwt_refreshToken;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        private Long id;
        private String loginId;
        private String grantType;
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class Simple {
        private String loginId;
        private String password;
        private String name;
        private String email;
        private String phone;
        private String birth;
        private String gender;
//        private MultipartFile profileImage;
        private String stageName;
//        private String savedName;
        @Builder
        public Simple(String loginId, String password, String name, String email, String phone, String birth, String gender, String stageName){
            this.loginId = loginId;
            this.password = password;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.birth = birth;
            this.gender = gender;
//            this.profileImage = profileImage;
            this.stageName = stageName;
//            this.savedName = savedName;
        }
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class checkIdResult {
        private Boolean isDuplicate;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class checkId {
        private String id;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getPkId{
        private Long id;
        private String loginId;
        private String name;
        private String email;
        private String phone;
        private String birth;
        private String gender;
        private String profileImage;
        private String stageName;
        private LocalDateTime createdAt;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRequest {
        private String loginId;
        private String name;
        private String email;
        private String phone;
        private String birth;
        private String gender;
        private String stageName;
        // 필요한 다른 정보들을 추가할 수 있습니다.
    }

}
