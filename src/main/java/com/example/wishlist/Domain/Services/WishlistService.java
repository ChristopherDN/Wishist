package com.example.wishlist.Domain.Services;
import com.example.wishlist.Data.Repo.SQLWishlist;
import com.example.wishlist.Domain.Models.User;
import com.example.wishlist.Domain.Models.Wishlist;

import java.util.ArrayList;


public class WishlistService {
  SQLWishlist sqlWishlist = new SQLWishlist();
  int wishlistId;

  public void deleteWishlist(User user,String event) {
    sqlWishlist.deleteWishlist(user, event);
    user.setMyWishlists(sqlWishlist.loadWishlists(user));
  }

  public void createWishlist(User user, String event) {
    addWishlistUser(user, event);
    sqlWishlist.createWishlist(user, event);
  }

  public void addWishlistUser(User user, String event){
    user.addWishlist(new Wishlist(event));
  }

  public ArrayList<Wishlist> getWishlists(User user) {
    user.setMyWishlists(sqlWishlist.loadWishlists(user));
    return user.getWishlist();
  }

  public void setWishlistId(User user, String event) {
    wishlistId = sqlWishlist.setGetWishlist(user, event);
    for (int i = 0; i <user.getWishlist().size() ; i++) {
      if (user.getWishlist().get(i).getEvent().equals(event)) {
        user.getWishlist().get(i).setId(wishlistId);
      }
    }
  }
}
