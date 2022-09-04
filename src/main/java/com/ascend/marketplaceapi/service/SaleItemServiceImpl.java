package com.ascend.marketplaceapi.service;

import com.ascend.marketplaceapi.model.SaleItem;
import com.ascend.marketplaceapi.repository.SaleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

  // TODO: CREATE @ControllerAdvice and custom exceptiosn to use in Service
  @Override
  public SaleItem getById(SaleItem saleItem) {

      Optional<SaleItem> item = repo.findById(saleItem.getItemId());
      if (!item.isPresent()) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, "ID not found for item"
        );
      }
      return item.get();
  }


}
