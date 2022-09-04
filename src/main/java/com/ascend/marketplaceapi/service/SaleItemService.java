package com.ascend.marketplaceapi.service;

import com.ascend.marketplaceapi.model.SaleItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SaleItemService {
  List<SaleItem> getAll();

  SaleItem getById(SaleItem saleItem);

  SaleItem create(SaleItem saleItem);

  SaleItem update(SaleItem saleItem);
}
