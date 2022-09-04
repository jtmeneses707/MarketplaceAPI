package com.ascend.marketplaceapi.service;

import com.ascend.marketplaceapi.exception.ItemAlreadyExistsException;
import com.ascend.marketplaceapi.exception.ItemNotFoundException;
import com.ascend.marketplaceapi.model.SaleItem;
import com.ascend.marketplaceapi.repository.SaleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleItemServiceImpl implements SaleItemService {

  @Autowired
  SaleItemRepository repo;

  @Override
  public List<SaleItem> getAll() {
    return repo.findAll();
  }

  @Override
  public SaleItem getById(SaleItem saleItem) {

      Optional<SaleItem> item = repo.findById(saleItem.getItemId());
      if (!item.isPresent()) {
        throw new ItemNotFoundException("Item not found in DB");
      }
      return item.get();
  }

  @Override
  public SaleItem create(SaleItem saleItem) {
    Optional<SaleItem> possibleMatch = repo.findById(saleItem.getItemId());
    if (possibleMatch.isPresent()) {
      throw new ItemAlreadyExistsException("Item with same ID already exists in DB");
    }
    return repo.save(saleItem);
  }

}
