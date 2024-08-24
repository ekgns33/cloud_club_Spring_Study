package com.CloudClub.jpaStudy.repository;

import com.CloudClub.jpaStudy.domain.member.Member;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

  private final EntityManager em;

  // Separate Command and Query

  public Member save(Member member) {
    em.persist(member);
    em.flush();
    return member;
  }

  public Member findOne(Long id) {
    return em.find(Member.class, id);
  }

  public List<Member> findAll() {
    return em.createQuery("select m from Member m", Member.class)
        .getResultList();
  }

  public List<Member> findByName(String name) {
    return em.createQuery("select m from Member m where m.name = :name", Member.class)
        .setParameter("name", name)
        .getResultList();
  }

  public Optional<Member> findByEmail(String email) {
    return em.createQuery("select m from Member m where m.email = :email", Member.class)
        .setParameter("email", email)
        .getResultList()
        .stream()
        .findFirst();
  }
}
