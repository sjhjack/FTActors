package com.a602.actors.global.jwt.service;

import java.util.Optional;

import com.a602.actors.domain.member.Member;
import com.a602.actors.global.exception.CustomException;
import com.a602.actors.global.exception.MemberException;
import com.a602.actors.global.exception.TokenException;
import com.a602.actors.global.jwt.JwtTokenProvider;
import com.a602.actors.global.jwt.dto.JwtDto;
import com.a602.actors.global.jwt.mapper.MemberMapper;
import com.a602.actors.global.jwt.repository.JWTMemberRepository;
import com.a602.actors.global.jwt.util.JWTUtil;
import com.a602.actors.global.jwt.util.TokenUtil;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.a602.actors.global.exception.ExceptionCodeSet.MEMBER_DUPLICATED;

@Service
@RequiredArgsConstructor
@Slf4j
public class JWTMemberServiceImpl {
    private final JWTMemberRepository jwtMemberRepository;
    private final MemberMapper memberMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenUtil tokenUtil;
    private final JWTUtil jwtUtil;

    @Transactional
    public String signup(JwtDto.Simple jwtDto) {
        String encodePassword = bCryptPasswordEncoder.encode(jwtDto.getPassword());
        log.info("encodePassword : {}", encodePassword);
        jwtDto.setPassword(encodePassword);
        isDuplicatedId(jwtDto.getUserId());
        jwtMemberRepository.save(memberMapper.MemberDtoToMember(jwtDto));
        return "";
    }

    @Transactional
    public JwtDto.AuthResponse signin(JwtDto.AuthRequest memberDto) {
        Member member = jwtMemberRepository.findByUserId(memberDto.getMemberId())
                .orElseThrow(() -> new CustomException("일치하는 사용자가 존재하지 않습니다."));
        log.info("로그인 시도한 멤버 :::::::::: {}, {}", member.getId(), member.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(memberDto.toAuthentication());
        // SecurityContextHolder에 로그인 한 유저 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("로그인 후 SecurityContextHolder에 저장된 사용자 :::::: {}",
                SecurityContextHolder.getContext().getAuthentication().getName());

        JwtDto.AuthResponse authResponse = jwtTokenProvider.genereateToken(authentication);
        log.info("Authentication : {}", authentication.toString());

        // Refresh Token Redis에 저장
        tokenUtil.setRefreshToken(authResponse.getRefreshToken());

        return authResponse;
    }

    public JwtDto.AuthResponse reIssue(JwtDto.AuthResponse tokenRequest) {
        log.info("MemberService :::::::::: reIssue 시작 !!!!!!!");
        // Refresh Token 파싱되면 OK
        jwtTokenProvider.validateToken(tokenRequest.getRefreshToken());
        log.info("MemberService :::::::::: 유효한 토큰");
        // Access Token 파싱해서 새로운 인증객체 만들기
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenRequest.getAccessToken());
        log.info("MemberService :::::::::: 인증객체 생성 {}", authentication.getName());
        // Redis에 저장되어있는 Refresh Token과 Request로 받은 Refresh Token 비교
        if (tokenUtil.checkRefreshTokenEquals(tokenRequest.getRefreshToken()) == false) {
            throw new TokenException("저장되어 있는 토큰과 일치하지 않습니다 !!!");
        }
        // 인증 객체로 토큰 재발행
        JwtDto.AuthResponse authResponse = jwtTokenProvider.genereateToken(authentication);

        return authResponse;
    }

    /**
     * 로그인 id 중복 검사
     * @param memberId : 로그인할 때 쓰는 아이디
     * @return boolean 중복 여부. true면 중복(가입불가), false면 중복 없음 (가입 가능)
     */
    public JwtDto.checkIdResult isDuplicatedId(String memberId) {
        Optional<Member> member = jwtMemberRepository.findByUserId(memberId);
        if (member.isPresent()) {
            throw new MemberException(MEMBER_DUPLICATED);
        }
        return new JwtDto.checkIdResult(false);
    }

    public boolean isAdminAccountExists(String adminUsername) {
        return jwtMemberRepository.findByUserId(adminUsername).isPresent();
    }

    public void logout() {
        if (tokenUtil.deleteRefreshToken() == false) {
            throw new RuntimeException("저장된 토큰을 삭제할 수 없습니다 !!!!!");
        }
    }

//    public void deleteMember() {
//        logout();
//
//        jwtMemberRepository.delete(
//                jwtMemberRepository.findById(jwtUtil.getLoginMemberId())
//                        .orElseThrow(() -> new RuntimeException("일치하는 사용자가 없습니다 !!!!!"))
//        );
//    }
}