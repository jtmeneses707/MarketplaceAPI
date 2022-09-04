package com.ascend.marketplaceapi;

import com.ascend.marketplaceapi.exception.ItemNotFoundException;
import com.ascend.marketplaceapi.model.SaleItem;
import com.ascend.marketplaceapi.service.SaleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequestMapping("api/item")
@RestController
public class SaleItemController {
  @Autowired
  SaleItemService saleItemService;

  @GetMapping("getbyid/{id}")
  ResponseEntity<HashMap<Object, Object>> getById(@RequestBody SaleItem saleItem, @PathVariable Integer id) {
    HashMap<Object, Object> resBody = new HashMap<>();

    // Check that ID in path URL matches ID in the body.
    if (!Objects.equals(id, saleItem.getItemId())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    SaleItem item = saleItemService.getById(saleItem);
    resBody.put("item", item);
    return ResponseEntity.status(HttpStatus.OK).body(resBody);
  }

  @ControllerAdvice
  @ResponseBody
  class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public HashMap<Object, Object> handleNotFound() {
      HashMap<Object,Object> resBody = new HashMap<>();
      resBody.put("cause", "ITEM DOES NOT EXIST");
      return resBody;
    }
  }


}
