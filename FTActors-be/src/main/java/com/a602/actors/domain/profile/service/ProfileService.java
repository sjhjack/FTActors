package com.a602.actors.domain.profile.service;

import com.a602.actors.domain.profile.dto.ProfileDto;
import com.a602.actors.domain.profile.dto.ProfileRequest;
import com.a602.actors.domain.profile.dto.ProfileSearchRequest;
import com.a602.actors.domain.profile.dto.ProfileSearchResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProfileService {
    List<ProfileSearchResponse> searchAllProfile(int sorting);

    List<ProfileSearchResponse> getProfileList();


    ProfileDto getProfile(Long profileId, HttpSession session);
    List<ProfileSearchResponse> search(ProfileSearchRequest profileSearchRequest);

    ///------------------------read

    String deleteProfile(Long profileId) throws IOException ;

    String createProfile(ProfileRequest profileRequest, MultipartFile image)  throws IOException;

    String updateProfile (ProfileRequest profileRequest, MultipartFile image) throws IOException ;

    List<ProfileSearchResponse> searchProfileByContent(List<String> keywords);
    List<ProfileSearchResponse> searchProfileByName(String keyword);
    List<ProfileSearchResponse> searchProfileByStageName(String findName);

    List<Long> getMyProfile(Long memberId);
}
