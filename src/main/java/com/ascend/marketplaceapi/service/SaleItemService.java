package com.ascend.marketplaceapi.service;

import com.ascend.marketplaceapi.model.SaleItem;

import java.util.List;

public interface SaleItemService {
  List<SaleItem> getAll();

  SaleItem getById(SaleItem saleItem);
}
