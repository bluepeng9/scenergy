package com.example.scenergynotification.domain.notification.service.commandresult;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendFollowNotificationCommandResult {

	Long notificationId;
	Long fromUserId;
	Long toUserId;

}
