package com.ascend.marketplaceapi;

import com.ascend.marketplaceapi.exception.ItemAlreadyExistsException;
import com.ascend.marketplaceapi.exception.ItemNotFoundException;
import com.ascend.marketplaceapi.model.SaleItem;
import com.ascend.marketplaceapi.service.SaleItemService;
import org.apache.coyote.Response;
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

  @GetMapping("create")
  ResponseEntity<HashMap<Object, Object>> create(@RequestBody SaleItem saleItem) {
    HashMap<Object, Object> resBody = new HashMap<>();
    SaleItem createdItem = saleItemService.create(saleItem);
    resBody.put("createdItem", createdItem);
    return ResponseEntity.status(HttpStatus.CREATED).body(resBody);
  }


  /**
   * Global exception handler. Handles all exceptions thrown by persistence layer (services).
   * Examples of custom created handled exceptions are ItemNotFound and ItemAlreadyExists
   */
  @ControllerAdvice
  @ResponseBody
  static
  class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public HashMap<Object, Object> handleNotFound() {
      HashMap<Object,Object> resBody = new HashMap<>();
      resBody.put("cause", "ITEM DOES NOT EXIST");
      return resBody;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ItemAlreadyExistsException.class)
    public HashMap<Object,Object> handleAlreadyExists() {
      HashMap<Object,Object> resBody = new HashMap<>();
      resBody.put("cause", "ITEM WITH SAME ID ALREADY EXISTS");
      return resBody;
    }

    // Normally occurs when missing a needed field in a request body.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public HashMap<Object, Object> handleIllegalArgument() {
      HashMap<Object, Object> resBody = new HashMap<>();
      resBody.put("cause", "REQUEST BODY MISSING NEEDED PARAMETERS");
      return resBody;
    }
  }
}
