package com.CloudClub.jpaStudy.service.follow.model;

import com.CloudClub.jpaStudy.domain.member.Connection;
import com.CloudClub.jpaStudy.service.follow.model.response.CreateFollowResponse;
import org.springframework.stereotype.Component;

@Component
public class ConnectionMapper {

  public CreateFollowResponse toCreateFollowResponse(Connection connection) {
    return CreateFollowResponse.of(connection);
  }

}
