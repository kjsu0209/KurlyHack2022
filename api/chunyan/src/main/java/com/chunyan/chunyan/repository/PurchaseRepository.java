package com.chunyan.chunyan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chunyan.chunyan.dao.Bag;
import com.chunyan.chunyan.dao.Purchase;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, String> {
}
