package com.wbm.scenergyspring.domain.follow.entity;

import com.wbm.scenergyspring.domain.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	User from;

	@ManyToOne(fetch = FetchType.LAZY)
	User to;

	public static Follow createFollow(
		User from,
		User to
	) {
		Follow follow = new Follow();
		follow.from = from;
		follow.to = to;
		return follow;
	}

}
