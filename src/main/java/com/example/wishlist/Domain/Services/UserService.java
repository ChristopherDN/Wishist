package com.example.wishlist.Domain.Services;
import com.example.wishlist.Data.Repo.SQLUsers;
import com.example.wishlist.Domain.Models.User;


public class UserService {
  User user;
  SQLUsers sqlUsers = new SQLUsers();

  public void registerUser(String name, String email, String password){
    sqlUsers.registerUser(name, email, password);
  }

  public User validateLogin(String username, String password){
    user = sqlUsers.validateLogin(username,password);
    return user;
  }
}
