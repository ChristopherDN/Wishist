package com.example.wishlist.Data.Repo;

import com.example.wishlist.Data.Utility.DBManager;
import com.example.wishlist.Domain.Models.User;
import org.springframework.stereotype.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class SQLUsers {
  Connection connection;
  PreparedStatement ps;
  boolean bol;
  ResultSet rs;
  User user;

  public void query(String sqlCommand) {
    try {
      connection = DBManager.getConnection();
      ps = connection.prepareStatement(sqlCommand);
      bol = ps.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public ResultSet load(String sqlCommand) {
    try {
      connection = DBManager.getConnection();
      ps = connection.prepareStatement(sqlCommand);
      rs = ps.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
//throw new ExceptionService(ex.getMessage())
      //Chose not to use ExceptionService, and instead catch as early as possible.
    }
    return rs;
  }

  public User rsToUser(ResultSet rs) {
    try {
      user = null;
      while (rs.next()) {
        user = new User(rs.getString(1), rs.getString(2), rs.getString(3));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return user;
  }

  public User validateLogin(String n, String p) {
    return rsToUser(load("SELECT name, email, password FROM wishlist.users WHERE name = '" + n + "' AND password = '" + p + "'"));
  }

  public void registerUser(String name, String email, String password) {
    query("insert into wishlist.users(Name, Email, Password, type) values(" + "\"" + name + "\", \"" +
        email + "\", \"" + password + "\", \"" + 1 + "\")");
  }
}
