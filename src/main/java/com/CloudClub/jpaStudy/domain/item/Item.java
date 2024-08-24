package com.CloudClub.jpaStudy.domain.item;

import com.CloudClub.jpaStudy.domain.item.exception.NotEnoughStockException;
import com.CloudClub.jpaStudy.domain.member.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@Getter
@Setter
public abstract class Item extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  private String name;
  private int price;
  private int stockQuantity;

  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<>();

  /*
   * 재고를 증가
   *
   * */
  public void addStock(int quantity) {
    this.stockQuantity += quantity;
  }

  /*
   * 재고를 감소
   * */
  public void removeStock(int quantity) {
    int restStock = this.stockQuantity - quantity;
		if (restStock < 0) {
			throw new NotEnoughStockException("need more stock");
		}
    this.stockQuantity = restStock;
  }
}
