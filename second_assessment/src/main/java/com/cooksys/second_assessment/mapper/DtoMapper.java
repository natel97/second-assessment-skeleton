package com.cooksys.second_assessment.mapper;


import org.mapstruct.Mapper;

import com.cooksys.second_assessment.Dto.ContextDto;
import com.cooksys.second_assessment.Dto.CredentialsDto;
import com.cooksys.second_assessment.Dto.HashtagDto;
import com.cooksys.second_assessment.Dto.ProfileDto;
import com.cooksys.second_assessment.Dto.TweetDto;
import com.cooksys.second_assessment.Dto.UserDto;
import com.cooksys.second_assessment.entity.Context;
import com.cooksys.second_assessment.entity.Credential;
import com.cooksys.second_assessment.entity.Hashtag;
import com.cooksys.second_assessment.entity.Profile;
import com.cooksys.second_assessment.entity.Tweet;
import com.cooksys.second_assessment.entity.User;

@Mapper(componentModel = "spring")
public interface DtoMapper {
	
	ContextDto toContextDto(Context contextDao);
	Context toContext(ContextDto context);
	
	CredentialsDto toCredentialsDto(Credential credential);
	Credential toCredentials(CredentialsDto credentials);
	
	HashtagDto toHashtagDto(Hashtag hashtag);
	Hashtag toHashtag(HashtagDto hashtag);
	
	ProfileDto toProfileDto(Profile profile);
	Profile toProfile(ProfileDto profile);
	
	TweetDto toTweetDto(Tweet tweet);
	Tweet toTweet(TweetDto Tweet);
	
	UserDto toUserDto(User user);
	User toUser(UserDto user);
	
}
