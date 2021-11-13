package com.example.wishlist.Data.Repo;

import com.example.wishlist.Data.Utility.DBManager;
import com.example.wishlist.Domain.Models.User;
import org.springframework.stereotype.Controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Controller
public class SQLUsers {
  Connection connection;
  PreparedStatement ps;
  boolean bol;
  ResultSet rs;
  ArrayList<User> users = new ArrayList<>();
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

  //throws ExceptionService
  //Skal ændres så den ikke sender en sql statement, men laver et run() kald i stedet.
  public ResultSet load(String sqlCommand) {
    try {
      connection = DBManager.getConnection();
      ps = connection.prepareStatement(sqlCommand);
      rs = ps.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
//throw new ExceptionService(ex.getMessage())
    }
    return rs;
  }

  public ArrayList<User> getResults(ResultSet rs) {
    try {
      users.clear();
      while (rs.next()) {
        users.add(new User(rs.getString(2), rs.getString(3), rs.getString(4)));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return users;
  }

  public User getUser(ResultSet rs) {
    try {
      user = null;
      while (rs.next()) {
        user = new User(rs.getString(1), rs.getString(2), rs.getString(3));
        users.add(user);
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return user;
  }

  public User validate(String n, String p) {
    return getUser(load("SELECT name, email, password FROM wishlist.users WHERE name = '" + n + "' AND password = '" + p + "'"));
  }

  public void createAccount(String name, String email, String password) {
    query("insert into wishlist.users(Name, Email, Password, type) values(" + "\"" + name + "\", \"" +
        email + "\", \"" + password + "\", \"" + 1 +  "\")");
  }
}
