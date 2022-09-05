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
    return getById(saleItem.getItemId());
  }

  @Override
  public SaleItem getById(Integer id) {
    Optional<SaleItem> item = repo.findById(id);
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


  /**
   * Updates the entry to match what was passed in the parameter.
   * Fulfills PUT functionality, except will not create a new object if the object did not already exist.
   *
   * @param saleItem Request body which will be used to update the entry in the DB. Expects at least ID to be present.
   * @return The newly updated entry.
   */
  @Override
  public SaleItem update(SaleItem saleItem) {
    Optional<SaleItem> possibleMatch = repo.findById(saleItem.getItemId());
    if (!possibleMatch.isPresent()) {
      throw new ItemNotFoundException("Item not found in DB");
    }
    return repo.save(saleItem);
  }

  /**
   * Performs a PATCH operation on only the soldTo field.
   *
   * @param saleItem Request Body where only soldTo field is expected.
   * @return Newly updated entry.
   */
  @Override
  public SaleItem setSold(SaleItem saleItem) {
    Optional<SaleItem> possibleMatch = repo.findById(saleItem.getItemId());
    if (!possibleMatch.isPresent()) {
      throw new ItemNotFoundException("Item not found in DB");
    }

    SaleItem updatedSaleItem = possibleMatch.get();
    updatedSaleItem.setSoldTo(saleItem.getSoldTo());
    return repo.save(updatedSaleItem);

  }

  @Override
  public void delete(Integer id) {
    SaleItem item = new SaleItem();
    item.setItemId(id);
    delete(item);
  }

  @Override
  public void delete(SaleItem saleItem) {
    Optional<SaleItem> possibleMatch = repo.findById(saleItem.getItemId());
    if (!possibleMatch.isPresent()) {
      throw new ItemNotFoundException("Item not found in DB");
    }
    repo.delete(saleItem);
  }

}
