package com.wbm.scenergyspring.domain.follow.entity;

import com.wbm.scenergyspring.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "follow_id")
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	User from;

	@ManyToOne(fetch = FetchType.LAZY)
	User to;
}
