package com.wbm.scenergyspring.domain.user.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.domain.tag.entity.LocationTag;
import com.wbm.scenergyspring.domain.tag.repository.LocationTagRepository;
import com.wbm.scenergyspring.domain.user.entity.User;
import com.wbm.scenergyspring.domain.user.entity.UserLocationTag;
import com.wbm.scenergyspring.domain.user.repository.UserRepository;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;
import com.wbm.scenergyspring.domain.user.service.commanresult.FindUserCommandResult;
import com.wbm.scenergyspring.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.io.IOException;
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
		createUserLocationTags(command.getLocationTagIds(), result);
		return result.getId();
	}


	@Transactional(readOnly = false)
	public Long deleteUser(String password, String username) {
		User user = userRepository.findByUsername(username);
		if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
			userRepository.delete(user);
		}
		return 1L;
	}

	public FindUserCommandResult findUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다.")
		);

		return FindUserCommandResult.builder()
			.nickname(user.getNickname())
			.build();
	}

	private void createUserLocationTags(List<Long> locationTagIds, User user) {
		List<UserLocationTag> userLocationTags = user.getUserLocationTags();
		userLocationTags.clear();

		for (Long locationTagId : locationTagIds) {
			LocationTag locationTag = locationTagRepository.findById(locationTagId)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 지역태그 입니다."));

			UserLocationTag userLocationTag = new UserLocationTag();
			userLocationTag.updateUser(user);
			userLocationTag.updateLocationTag(locationTag);

			userLocationTags.add(userLocationTag);
		}
		user.updateUserLocationTag(userLocationTags);;
	}
}
