package com.wbm.scenergyspring.domain.user.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.wbm.scenergyspring.domain.tag.entity.LocationTag;
import com.wbm.scenergyspring.domain.tag.repository.LocationTagRepository;
import com.wbm.scenergyspring.domain.tag.repository.UserLocationTagRepository;
import com.wbm.scenergyspring.domain.user.controller.request.SearchFollowingRequest;
import com.wbm.scenergyspring.domain.user.controller.response.SearchFollowingAllResponse;
import com.wbm.scenergyspring.domain.user.controller.response.SearchFollowingResponse;
import com.wbm.scenergyspring.domain.user.controller.response.SearchUserResponse;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.entity.UserLocationTag;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;
import com.wbm.scenergyspring.domain.user.service.command.UpdateUserInfoCommand;
import com.wbm.scenergyspring.domain.user.service.command.UploadBioCommand;
import com.wbm.scenergyspring.domain.user.service.command.UploadProfileCommand;
import com.wbm.scenergyspring.domain.user.service.commanresult.FindUserCommandResult;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	final BCryptPasswordEncoder bCryptPasswordEncoder;
	final UserRepository userRepository;
	final LocationTagRepository locationTagRepository;
	final AmazonS3Client amazonS3Client;
	final UserLocationTagRepository userLocationRepository;


	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Transactional(readOnly = false)
	public String uploadProfileS3(UploadProfileCommand command) {
		try {
			String profileName = UUID.randomUUID().toString().replace("-", "") + StringUtils.cleanPath(Objects.requireNonNull(command.getMultipartFile().getOriginalFilename()));

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(command.getMultipartFile().getContentType());
			metadata.setContentLength(command.getMultipartFile().getSize());
			amazonS3Client.putObject(bucket, "profile/" + profileName, command.getMultipartFile().getInputStream(), metadata);

			String profileUrlPath = amazonS3Client.getUrl(bucket, "profile/" + profileName).toString();
			userRepository.findById(command.getUserId()).get().updateUrl(profileUrlPath);
			return profileUrlPath;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = false)
	public Long createUser(CreateUserCommand command) {
		User newUser = User.createNewUser(
			command.getEmail(),
			bCryptPasswordEncoder.encode(command.getPassword()),
			command.getUsername(),
			command.getGender(),
			command.getNickname()
		);
		User result = userRepository.save(newUser);
		return result.getId();
	}

	@Transactional(readOnly = false)
	public String uploadBio(UploadBioCommand command) {
		User user = userRepository.findById(command.getUserId())
			.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
		user.updateBio(command.getBio());
		return user.getBio();
	}

	@Transactional(readOnly = false)
	public Long deleteUser(String password, String username) {
		User user = userRepository.findByUsername(username);
		if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
			Long tmpId = user.getId();
			userRepository.delete(user);
			return tmpId;
		}
		return user.getId();
	}

	public List<SearchUserResponse> searchUser(String word) {
		List<User> list = userRepository.searchUsers(word);
		List<SearchUserResponse> result = new ArrayList<>();
		for (User user : list) {
			SearchUserResponse response = SearchUserResponse.builder()
					.userId(user.getId())
					.nickName(user.getNickname())
					.url(user.getUrl())
					.build();
			result.add(response);
		}
		return result;
	}

	public List<SearchFollowingAllResponse> searchFollowingAll(Long userId) {
		List<User> list = userRepository.searchFollowingAll(userId);
		List<SearchFollowingAllResponse> result = new ArrayList<>();
		for (User user : list) {
			SearchFollowingAllResponse response = SearchFollowingAllResponse.builder()
					.userId(user.getId())
					.nickName(user.getNickname())
					.url(user.getUrl())
					.build();
			result.add(response);
		}
		return result;
	}

	public List<SearchFollowingResponse> searchFollowing(SearchFollowingRequest request) {
		List<User> list = userRepository.searchFollowing(request.getUserId(), request.getWord());
		List<SearchFollowingResponse> result = new ArrayList<>();
		for (User user : list) {
			SearchFollowingResponse response = SearchFollowingResponse.builder()
					.userId(user.getId())
					.nickName(user.getNickname())
					.url(user.getUrl())
					.build();
			result.add(response);
		}
		return result;
	}


	public FindUserCommandResult findUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다.")
		);

		return FindUserCommandResult.builder()
			.userId(user.getId())
			.userPassword(user.getPassword())
			.userName(user.getUsername())
			.userNickname(user.getNickname())
			.userGender(user.getGender())
			.url(user.getUrl())
			.build();
	}

	public void createUserLocationTags(List<Long> locationTagIds, User user) {
		if (locationTagIds != null) {
			for (Long locationTagId : locationTagIds) {
				LocationTag locationTag = locationTagRepository.findById(locationTagId)
					.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 지역태그 입니다."));

				UserLocationTag userLocationTag = new UserLocationTag();
				userLocationTag.updateUser(user);
				userLocationTag.updateLocationTag(locationTag);

				userLocationRepository.save(userLocationTag);
			}
		}
	}

	@Transactional(readOnly = false)
	public void updateUserInfo(UpdateUserInfoCommand command) {
		User user = userRepository.findById(command.getUserId()).orElseThrow(() -> new EntityNotFoundException("없는 유저 입니다."));
		if (command.getUserName() != null)
			user.updateUserName(command.getUserName());
		if (command.getNickname() != null)
			user.updateNickname(command.getNickname());
	}
}
