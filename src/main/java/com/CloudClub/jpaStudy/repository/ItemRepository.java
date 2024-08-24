package com.CloudClub.jpaStudy.repository;

import com.CloudClub.jpaStudy.domain.item.Item;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

  private final EntityManager em;

  public void save(Item item) {
    if (item.getId() == null) { //완전 새롭게 등록하는 객체
      em.persist(item);
    } else { // 이미 등록된 객체를 어디서 가져옴. > update랑 유사
      em.merge(item);
    }
  }

  public Item findOne(Long id) {
    return em.find(Item.class, id);
  }

  public List<Item> findAll() {
    return em.createQuery("select i from Item i", Item.class)
        .getResultList();
  }
}
