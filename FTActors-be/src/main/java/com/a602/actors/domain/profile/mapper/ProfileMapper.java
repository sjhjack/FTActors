package com.a602.actors.domain.profile.mapper;

import com.a602.actors.domain.profile.dto.ProfileDto;
import com.a602.actors.domain.profile.dto.ProfileSearchResponse;
import com.a602.actors.domain.profile.entity.Profile;
import com.a602.actors.domain.profile.entity.ProfileDocument;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProfileMapper {
    public List<ProfileDto> ProfileListToProfileDtoList(List<Profile> profiles) {
        if ( profiles == null ) {
            return null;
        }

        List<ProfileDto> list = new ArrayList<ProfileDto>( profiles.size() );
        for ( Profile profile : profiles ) {
            list.add( ProfileToProfileDto( profile ) );
        }

        return list;
    }

    public ProfileDto ProfileToProfileDto(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        ProfileDto.ProfileDtoBuilder profileDto = ProfileDto.builder();

        profileDto.id( profile.getId() );
        profileDto.memberId( profile.getMember().getId());
        profileDto.name(profile.getMember().getName());
        profileDto.stageName(profile.getMember().getStageName());
        profileDto.content( profile.getContent() );
        profileDto.type( profile.getType() );
        profileDto.gender(profile.getMember().getGender());
        profileDto.portfolio( profile.getPortfolio() );
        profileDto.birth(profile.getMember().getBirth());
        profileDto.privatePost( profile.getPrivatePost() );
        profileDto.imageLink(profile.getImage());
        profileDto.createdTime(profile.getCreatedAt().toString());

        return profileDto.build();
    }

    public List<ProfileSearchResponse> ProfileDocumentListToProfileSearchResponseList(List<ProfileDocument> profileDocuments) {
        if ( profileDocuments == null ) {
            return null;
        }

        List<ProfileSearchResponse> list = new ArrayList<ProfileSearchResponse>( profileDocuments.size() );
        for ( ProfileDocument profileDocument : profileDocuments ) {
            list.add( ProfileDocumentToProfileSearchResponse( profileDocument ) );
        }

        return list;
    }

    public ProfileSearchResponse ProfileDocumentToProfileSearchResponse(ProfileDocument profileDocument) {
        if ( profileDocument == null ) {
            return null;
        }

        // profileDocument.getCreatedTime()에서 LocalDateTime 객체 가져오기
        LocalDateTime createdTime = profileDocument.getCreatedTime();
        LocalDateTime updatedTime = profileDocument.getUpdatedTime();

        // LocalDateTime 객체를 String으로 변환하기 위해 DateTimeFormatter 사용
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createdTimeString = createdTime.format(formatter);
        String updatedTimeString = updatedTime.format(formatter);

        return ProfileSearchResponse.builder()
                .id(profileDocument.getId())
                .name(profileDocument.getName())
                .stageName(profileDocument.getStageName())
                .content(profileDocument.getContent())
                .type(profileDocument.getType())
                .privatePost(profileDocument.getPrivatePost())
                .createdTime(createdTimeString)
                .updatedTime(updatedTimeString)
                .gender(profileDocument.getGender())
                .birth(profileDocument.getBirth())
                .build();
    }
    public List<ProfileSearchResponse> ProfileToProfileSearchResponseList(List<Profile> profiles) {
        if ( profiles == null ) {
            return null;
        }

        List<ProfileSearchResponse> list = new ArrayList<ProfileSearchResponse>( profiles.size() );
        for ( Profile profile : profiles ) {
            list.add( ProfileToProfileSearchResponse( profile) );
        }

        return list;
    }
    public ProfileSearchResponse ProfileToProfileSearchResponse(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        return ProfileSearchResponse.builder()
                .id(profile.getId())
                .memberId(profile.getMember().getId())
                .name(profile.getMember().getName())
                .stageName(profile.getMember().getStageName())
                .content(profile.getContent())
                .type(profile.getType())
                .privatePost(profile.getPrivatePost())
                .imageLink(profile.getImage())
                .createdTime(profile.getCreatedAt().toString())
                .gender(profile.getMember().getGender())
                .birth(profile.getMember().getBirth())
                .build();
    }

}
