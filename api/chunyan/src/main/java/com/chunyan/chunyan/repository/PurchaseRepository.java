package com.chunyan.chunyan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chunyan.chunyan.dao.Bag;
import com.chunyan.chunyan.dao.Purchase;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, String> {

	@Query("SELECT p FROM Purchase p WHERE p.user_id = :user_id "
		+ "AND p.purchase_dt >= function('to_date', :sDate, 'YYYYMMDD') "
		+ "AND p.purchase_dt <= function('to_date', :eDate, 'YYYYMMDD')")
	List<Purchase> findAllByRange(String user_id, String sDate, String eDate);

	@Query("SELECT p FROM Purchase p JOIN Bag b ON p.purchase_id = b.purchase_id "
		+ "WHERE p.user_id = :user_id "
		+ "AND p.purchase_dt >= function('to_date', :sDate, 'YYYYMMDD') "
		+ "AND p.purchase_dt <= function('to_date', :eDate, 'YYYYMMDD')"
		+ "AND b.is_sample = :sample")
	List<Purchase> findAllByRangeAndSample(String user_id, String sDate, String eDate, Boolean sample);
}
