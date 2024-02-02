package com.example.scenergynotification.domain.notification.entity;

import com.example.scenergynotification.domain.user.entity.User;
import com.example.scenergynotification.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User receiver;

	@ManyToOne(fetch = FetchType.LAZY)
	private User sender;


	@Enumerated(EnumType.STRING)
	private NotificationStatus status;

	void updateNotificationInfo(
			User receiver,
			User sender,
			NotificationStatus status
	) {
		this.receiver = receiver;
		this.sender = sender;
		this.status = status;
	}

	public void readNotification() {
		this.status = NotificationStatus.READ;
	}

}
