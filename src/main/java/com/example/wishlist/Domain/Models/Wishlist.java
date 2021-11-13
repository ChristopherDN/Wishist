package com.example.wishlist.Domain.Models;


public class Wishlist {
  private String event;
  private int id;

  public Wishlist(String event) {
    this.event = event;
  }

  public Wishlist(String event, int id) {
    this.event = event;
    this.id = id;
  }

  public String getEvent() {
    return event;
  }

  @Override
  public String toString() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
