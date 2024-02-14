package com.example.scenergynotification.domain.notification.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class LikeNotification extends Notification {

	private Long postId;
}