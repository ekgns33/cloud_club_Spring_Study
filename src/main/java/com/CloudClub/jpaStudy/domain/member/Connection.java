package com.CloudClub.jpaStudy.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Connection extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member from;

  @Column(name = "following_id")
  private Long followingMemberId;

  @Enumerated(EnumType.STRING)
  private ConnectionType connectionType;

  public static Connection createFollow(Member from, Member to) {
    Connection connection = new Connection();
    connection.from = from;
    connection.followingMemberId = to.getId();
    connection.connectionType = ConnectionType.FOLLOW;

    from.addFollowing();
    return connection;
  }
}
