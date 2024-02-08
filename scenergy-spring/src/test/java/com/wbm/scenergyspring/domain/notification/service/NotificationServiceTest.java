package com.wbm.scenergyspring.domain.notification.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wbm.scenergyspring.IntegrationTest;
import com.wbm.scenergyspring.domain.notification.entity.NotificationStatus;
import com.wbm.scenergyspring.domain.notification.repository.NotificationRepository;
import com.wbm.scenergyspring.domain.notification.service.command.SendFollowNotificationCommand;
import com.wbm.scenergyspring.domain.user.service.UserService;
import com.wbm.scenergyspring.domain.user.service.command.CreateUserCommand;
import com.wbm.scenergyspring.util.RandomValueGenerator;

@SpringBootTest
class NotificationServiceTest extends IntegrationTest {

	@Autowired
	UserService userService;
	@Autowired
	NotificationService notificationService;
	@Autowired
	NotificationRepository notificationRepository;

	Long saveUser() {
		return userService.createUser(
			CreateUserCommand.builder()
				.email(RandomValueGenerator.generateRandomEmail())
				.password("1234")
				.nickname(RandomValueGenerator.generateRandomString(10, 20))
				.build());
	}

	@Test
	@Transactional
	void sendFollowNotification() {
		//given
		Long toUserId = saveUser();
		Long fromUserId = saveUser();

		SendFollowNotificationCommand command = SendFollowNotificationCommand.builder()
			.toUserId(toUserId)
			.fromUserId(fromUserId)
			.build();

		//when
		notificationService.sendFollowNotification(command);

		//then
		Assertions.assertEquals(1, notificationRepository.count());

	}

	@Test
	@Transactional
	void readNotification() {

		//given
		Long toUserId = saveUser();
		Long fromUserId = saveUser();

		SendFollowNotificationCommand command = SendFollowNotificationCommand.builder()
			.toUserId(toUserId)
			.fromUserId(fromUserId)
			.build();

		Long notificationId = notificationService.sendFollowNotification(command);

		//when
		notificationService.readNotification(notificationId);

		//then
		Assertions.assertEquals(1, notificationRepository.count());
		Assertions.assertEquals(NotificationStatus.READ,
			notificationRepository.findById(notificationId).get().getStatus());
	}
}
