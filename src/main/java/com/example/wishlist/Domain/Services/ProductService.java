package com.example.wishlist.Domain.Services;

import com.example.wishlist.Data.Repo.SQLProducts;
import com.example.wishlist.Data.Repo.SQLWishlist;
import com.example.wishlist.Domain.Models.Product;
import com.example.wishlist.Domain.Models.User;

import java.util.ArrayList;


public class ProductService {
  SQLProducts sqlProducts = new SQLProducts();
  SQLWishlist sqlWishlist = new SQLWishlist();
  int wishlistId;

  public void deleteProduct(String event, String productName) {
    sqlProducts.deleteProduct(event, productName);
  }

  public void createProduct(String name, String description, String price, String url, User user, String event) {
    wishlistId = sqlWishlist.setGetWishlist(user, event);
    sqlProducts.createProduct(name, description, price, url, wishlistId );
  }

  public ArrayList<Product> getProducts(User user, String event) {
    for (int i = 0; i < user.getWishlist().size(); i++) {
      if (user.getWishlist().get(i).getEvent().equals(event)) {
        wishlistId = user.getWishlist().get(i).getId();
        return sqlProducts.getProducts(wishlistId);
      }
    }
    return sqlProducts.getProducts(0);
  }
}
