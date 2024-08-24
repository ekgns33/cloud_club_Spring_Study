package com.CloudClub.jpaStudy.repository;

import com.CloudClub.jpaStudy.domain.member.Connection;
import com.CloudClub.jpaStudy.domain.member.Member;
import com.CloudClub.jpaStudy.service.follow.model.response.FollowingInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ConnectionRepository {

  private final EntityManager em;

  public void save(Connection connection) {
    em.persist(connection);
    em.flush();
  }

  public Optional<Connection> findFollowingByMember(Member member, Long targetId) {
    return Optional.ofNullable(
        em.createQuery("select c from Connection c where c.from = :member"
                + " and c.followingMemberId = :targetId", Connection.class)
            .setParameter("targetId", targetId)
            .setParameter("member", member)
            .getSingleResult());
  }

  public List<FollowingInfo> findFollowingByMemberId(Long memberId) {
    String jpql =
        "SELECT new com.CloudClub.jpaStudy.service.follow.model.response.FollowingInfo(m1.id, m2.name) "
            +
            "FROM Member m1 " +
            "JOIN Connection c ON m1.id = c.from.id " +
            "JOIN Member m2 ON c.followingMemberId = m2.id " +
            "WHERE m1.id = :memberId";
    TypedQuery<FollowingInfo> query = em.createQuery(jpql, FollowingInfo.class)
        .setParameter("memberId", memberId);
    return query.getResultList();
  }

  public void delete(Connection connection) {
    em.remove(connection);
  }
}
