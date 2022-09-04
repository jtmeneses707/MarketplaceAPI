package com.ascend.marketplaceapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class SaleItem {
  @Id
  Integer itemId;

  String soldBy, name, description;

  Float price;

  String soldTo;

  @Override
  public String toString() {
    return "SaleItem{" +
        "itemId=" + itemId +
        ", soldBy='" + soldBy + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", price=" + price +
        ", soldTo='" + soldTo + '\'' +
        '}';
  }

  public Integer getItemId() {
    return itemId;
  }

  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }

  public String getSoldBy() {
    return soldBy;
  }

  public void setSoldBy(String soldBy) {
    this.soldBy = soldBy;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public String getSoldTo() {
    return soldTo;
  }

  public void setSoldTo(String soldTo) {
    this.soldTo = soldTo;
  }
}
