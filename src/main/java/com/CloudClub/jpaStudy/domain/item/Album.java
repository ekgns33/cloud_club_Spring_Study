package com.CloudClub.jpaStudy.domain.item;

import com.CloudClub.jpaStudy.domain.Item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("A")
@Getter @Setter
public class Album extends Item {

	private String artist;
	private String etc;
}
