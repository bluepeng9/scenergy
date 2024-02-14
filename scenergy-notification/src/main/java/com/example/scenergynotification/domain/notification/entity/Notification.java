package com.example.scenergynotification.domain.notification.entity;

import com.example.scenergynotification.global.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "notifications")
@Getter
public abstract class Notification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long receiver;

	private Long sender;

	private String senderNickname;

	@Enumerated(EnumType.STRING)
	private NotificationStatus status;

	void updateNotificationInfo(
		Long receiver,
		Long sender,
		NotificationStatus status,
		String senderNickname
	) {
		this.receiver = receiver;
		this.sender = sender;
		this.status = status;
		this.senderNickname = senderNickname;
	}

	public void readNotification() {
		this.status = NotificationStatus.READ;
	}

}
