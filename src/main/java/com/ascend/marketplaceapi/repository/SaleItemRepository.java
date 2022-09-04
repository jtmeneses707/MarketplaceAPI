package com.ascend.marketplaceapi.repository;

import com.ascend.marketplaceapi.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Integer> {
}
