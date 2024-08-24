package com.CloudClub.jpaStudy.domain.member;

import com.CloudClub.jpaStudy.domain.Team;
import com.CloudClub.jpaStudy.domain.order.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;
  private String name;

  @Column(name = "member_email", unique = true)
  private String email;

  @Embedded
  private Address address;

  @OneToMany(mappedBy = "member")
  private List<Order> orders = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "member_role")
  private MemberRole memberRole;

  private Integer followerCount;
  private Integer followingCount;

  @Builder
  public Member(String name, String email) {
    this.name = name;
    this.email = email;
    this.memberRole = MemberRole.USER;
    this.followerCount = 0;
    this.followingCount = 0;
  }

  public void addFollower() {
    this.followerCount++;
  }

  public void addFollowing() {
    this.followingCount++;
  }

  public void removeFollower() {
    if (this.followerCount <= 0) {
      throw new IllegalStateException("팔로워 수가 0보다 작을 수 없습니다.");
    }
    this.followerCount--;
  }

}
