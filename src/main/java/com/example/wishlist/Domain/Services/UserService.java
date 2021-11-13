package com.example.wishlist.Domain.Services;
import com.example.wishlist.Data.Repo.SQLUsers;
import com.example.wishlist.Domain.Models.User;


public class UserService {
  User user;
  SQLUsers sqlUsers = new SQLUsers();

  public void createAccount(String name, String email, String password){
    sqlUsers.createAccount(name, email, password);
  }

  public User validate(String username, String password){
    user = sqlUsers.validate(username,password);
    return user;
  }
}
