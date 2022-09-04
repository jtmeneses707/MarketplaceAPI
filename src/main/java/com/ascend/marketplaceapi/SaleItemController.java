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

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// TODO: DECIDE IF I WANT TO REFACTOR AND USE @ResponseStatus ANNOTATION
// MAY MAKE CODE CLEANE

// TODO: DECIDE IF ID IS NEEDED IN THE REQUEST BODY, OR IF SHOULD SOLELY RELY ON ID IN PATH.

// TODO: DECIDE IF REFACTOR ENDPOINTS SO THAT THEY ALL SHARE SAME URL
// EX: Can do POST, GET, DELETE, and PUT all from /api/item

@RequestMapping("api/item")
@RestController
public class SaleItemController {
  @Autowired
  SaleItemService saleItemService;

  @GetMapping("getall")
  ResponseEntity<HashMap<Object,Object>> getAll() {
    HashMap<Object,Object> resBody = new HashMap<>();
    resBody.put("items", saleItemService.getAll());
    return ResponseEntity.status(HttpStatus.OK).body(resBody);
  }

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

  @PostMapping("create")
  ResponseEntity<HashMap<Object, Object>> create(@RequestBody SaleItem saleItem) {
    HashMap<Object, Object> resBody = new HashMap<>();
    SaleItem createdItem = saleItemService.create(saleItem);
    resBody.put("createdItem", createdItem);
    return ResponseEntity.status(HttpStatus.CREATED).body(resBody);
  }

  @PutMapping("put/{id}")
  ResponseEntity<HashMap<Object, Object>> put(@RequestBody SaleItem saleItem, @PathVariable Integer id) {

    // Check that ID in path URL matches ID in the body.
    if (!Objects.equals(id, saleItem.getItemId())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    HashMap<Object, Object> resBody = new HashMap<>();
    SaleItem updatedItem = saleItemService.update(saleItem);
    resBody.put("updatedItem", updatedItem);
    return ResponseEntity.status(HttpStatus.OK).body(resBody);
  }


  @DeleteMapping("delete/{id}")
  ResponseEntity<HashMap<Object, Object>> delete(@PathVariable Integer id) {

    HashMap<Object, Object> resBody = new HashMap<>();
    saleItemService.delete(id);
    resBody.put("count", 1);
    return ResponseEntity.status(200).body(resBody);
  }


  /**
   * Global exception handler. Handles all exceptions thrown by persistence layer (services).
   * These errors are of type RuntimeException.
   * Examples of custom created handled exceptions are ItemNotFound and ItemAlreadyExists
   */
  @ControllerAdvice
  @ResponseBody
  static
  class GlobalControllerExceptionHandler {

    // Thrown when business logic cannot occur if item does not exist.
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public HashMap<Object, Object> handleNotFound() {
      HashMap<Object,Object> resBody = new HashMap<>();
      resBody.put("cause", "ITEM DOES NOT EXIST");
      return resBody;
    }

    // Thrown when business logic cannot occur if item already exists.
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
