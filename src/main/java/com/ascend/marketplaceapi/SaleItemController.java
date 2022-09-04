package com.ascend.marketplaceapi;

import com.ascend.marketplaceapi.model.SaleItem;
import com.ascend.marketplaceapi.service.SaleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/item")
@RestController
public class SaleItemController {
  @Autowired
  SaleItemService saleItemService;

  @GetMapping("getbyid")
  SaleItem getById(@RequestBody SaleItem saleItem) {
//    try {
      return saleItemService.getById(saleItem);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    return null;
  }


}
