package com.example.wishlist.Domain.Models;


public class Product {
  private String name;
  private String description;
  private int price;
  private String url;

  public Product(String name, String description, int price, String url) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.url = url;
  }


  public void setName(String name) {
    this.name = name;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getPrice() {
    return price;
  }

  public String getUrl() {
    return url;
  }

  @Override
  public String toString() {
    return name + ". Description: " + description + ". Price=: " + price + ". url='" + url;
  }
}
