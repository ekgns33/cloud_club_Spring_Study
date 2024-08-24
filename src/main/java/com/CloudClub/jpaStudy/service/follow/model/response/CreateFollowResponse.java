package com.CloudClub.jpaStudy.service.follow.model.response;

import com.CloudClub.jpaStudy.domain.member.Connection;
import com.CloudClub.jpaStudy.domain.member.ConnectionType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateFollowResponse {

  private final Long id;

  private final LocalDateTime followedAt;

  private final ConnectionType connectionType;

  @Builder
  private CreateFollowResponse(Long id, LocalDateTime followedAt, ConnectionType connectionType) {
    this.id = id;
    this.followedAt = followedAt;
    this.connectionType = connectionType;
  }

  public static CreateFollowResponse of(Connection connection) {
    return CreateFollowResponse.builder()
        .id(connection.getId())
        .followedAt(connection.getLastModifiedDate())
        .connectionType(connection.getConnectionType())
        .build();
  }
}
