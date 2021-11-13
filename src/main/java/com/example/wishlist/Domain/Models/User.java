package com.example.wishlist.Domain.Models;

import java.util.ArrayList;


public class User {
  private String name;
  private String email;
  private String password;
  private ArrayList<Wishlist> myWishlists = new ArrayList<>();

  public User(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }
//TODO Tror ikke vi har brug for denne her?? Systemet virker lige nu, uden brug af den.
  public User(String name, String email, String password, ArrayList<Wishlist> myWishlists) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.myWishlists = myWishlists;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void addWishlist(Wishlist wishlist) {
    myWishlists.add(wishlist);
  }

  public ArrayList<Wishlist> getWishlist() {
    return myWishlists;
  }

  public void setMyWishlists(ArrayList<Wishlist> myWishlists) {
    this.myWishlists = myWishlists;
  }

  @Override
  public String toString() {
    return name + " Email: " + email + "Password: " + password;
  }




}
