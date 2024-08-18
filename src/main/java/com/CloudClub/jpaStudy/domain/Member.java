package com.CloudClub.jpaStudy.domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String name;

	@Column(name = "member_email", unique = true)
	private String email;

	@Embedded
	private Address address;

	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();

	@Enumerated(value =EnumType.STRING)
	@Column(name = "member_role")
	private MemberRole memberRole;

	@Builder
	public Member(String name, String email) {
		this.name = name;
		this.email = email;
		this.memberRole = MemberRole.USER;
	}
}
