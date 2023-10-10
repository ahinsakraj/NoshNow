package com.seavol.NoshNow.repository;

import com.seavol.NoshNow.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
