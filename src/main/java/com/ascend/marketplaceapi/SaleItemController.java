package com.ascend.marketplaceapi;

import com.ascend.marketplaceapi.exception.ItemAlreadyExistsException;
import com.ascend.marketplaceapi.exception.ItemNotFoundException;
import com.ascend.marketplaceapi.model.SaleItem;
import com.ascend.marketplaceapi.service.SaleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequestMapping("api/item")
@RestController
public class SaleItemController {
  @Autowired
  SaleItemService saleItemService;


  /**
   * Simple GET endpoint returning all SaleItem entries in the DB.
   *
   * @return JSON containing list of all items for sale.
   */
  @GetMapping("getall")
  ResponseEntity<HashMap<Object, Object>> getAll() {
    HashMap<Object, Object> res = new HashMap<>();
    res.put("items", saleItemService.getAll());
    return ResponseEntity.status(HttpStatus.OK).body(res);
  }

  /**
   * GET endpoint to query item by ID.
   *
   * @param id The ID of the item for sale to be queried.
   * @return JSON containing item to be queried.
   */
  @GetMapping("{id}")
  ResponseEntity<HashMap<Object, Object>> getById(@PathVariable Integer id) {
    HashMap<Object, Object> res = new HashMap<>();

    SaleItem item = saleItemService.getById(id);
    res.put("item", item);
    return ResponseEntity.status(HttpStatus.OK).body(res);
  }

  /**
   * POST endpoint to create an item for sale.
   *
   * @param req Request body that is cast to SaleItem for ease of use. ID field cannot be null.
   * @return JSON containing newly created item for sale.
   */
  @PostMapping()
  ResponseEntity<HashMap<Object, Object>> create(@RequestBody SaleItem req) {
    HashMap<Object, Object> res = new HashMap<>();
    SaleItem createdItem = saleItemService.create(req);
    res.put("createdItem", createdItem);
    return ResponseEntity.status(HttpStatus.CREATED).body(res);
  }

  /**
   * PUT endpoint to update an entire entry.
   *
   * @param req Request body that is cast to SaleItem for ease of use. ID field from the path variable takes precedence
   *            over any possible ID field in request body .
   * @param id  Path variable for the ID of the sale item to update.
   * @return JSON containing the updated sale item.
   */
  @PutMapping("{id}")
  ResponseEntity<HashMap<Object, Object>> put(@RequestBody SaleItem req, @PathVariable Integer id) {
    req.setItemId(id);
    HashMap<Object, Object> res = new HashMap<>();
    SaleItem updatedItem = saleItemService.update(req);
    res.put("updatedItem", updatedItem);
    return ResponseEntity.status(HttpStatus.OK).body(res);
  }


  /**
   * @param req Request body that is cast to SaleItem for ease of use. soldTo field cannot be null.
   * @param id  Path variable for the ID of the sale item to update.
   * @return JSON containing the updated sale item.
   */
  @PatchMapping("setsold/{id}")
  ResponseEntity<HashMap<Object, Object>> setSold(@RequestBody SaleItem req, @PathVariable Integer id) {
    req.setItemId(id);
    HashMap<Object, Object> res = new HashMap<>();

    if (req.getSoldTo().isEmpty()) {
      throw new IllegalArgumentException("soldTo property is missing");
    }

    SaleItem updatedItem = saleItemService.setSold(req);
    res.put("updatedItem", updatedItem);
    return ResponseEntity.status(HttpStatus.OK).body(res);
  }


  /**
   * DELETE endpoint for deleting a SaleItem.
   *
   * @param id Path variable containing the ID of the item to be deleted.
   * @return JSON containing the count of the number of items deleted.
   */
  @DeleteMapping("{id}")
  ResponseEntity<HashMap<Object, Object>> delete(@PathVariable Integer id) {
    HashMap<Object, Object> res = new HashMap<>();
    saleItemService.delete(id);
    res.put("count", 1);
    return ResponseEntity.status(200).body(res);
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
    public HashMap<Object, Object> handleNotFound(ItemNotFoundException exception) {
      HashMap<Object, Object> resBody = new HashMap<>();
      resBody.put("cause", exception.getMessage());
      return resBody;
    }

    // Thrown when business logic cannot occur if item already exists.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ItemAlreadyExistsException.class)
    public HashMap<Object, Object> handleAlreadyExists(ItemAlreadyExistsException exception) {
      HashMap<Object, Object> resBody = new HashMap<>();
      resBody.put("cause", exception.getMessage());
      return resBody;
    }

    // Normally occurs when missing a needed field in a request body.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public HashMap<Object, Object> handleIllegalArgument(IllegalArgumentException exception) {
      HashMap<Object, Object> resBody = new HashMap<>();
      resBody.put("cause", exception.getMessage());
      return resBody;
    }
  }
}
