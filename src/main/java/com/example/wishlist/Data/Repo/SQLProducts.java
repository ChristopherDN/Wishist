package com.example.wishlist.Data.Repo;

import com.example.wishlist.Data.Utility.DBManager;
import com.example.wishlist.Domain.Models.Product;
import org.springframework.stereotype.Controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Controller
public class SQLProducts {
  Connection connection;
  PreparedStatement ps;
  boolean bol;
  ResultSet rs;
  ArrayList<Product> products = new ArrayList<>();
  int userid;
  int wishlistId;

  public void query(String sqlCommand) {
    try {
      connection = DBManager.getConnection();
      ps = connection.prepareStatement(sqlCommand);
      bol = ps.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public ResultSet load(String sqlCommand)  {
    try {
      connection = DBManager.getConnection();
      ps = connection.prepareStatement(sqlCommand);
      rs = ps.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      //throw new ExceptionService(ex.getMessage());
      //Chose not to use ExceptionService, and instead catch as early as possible.
    }
    return rs;
  }

  public ArrayList<Product> rsToArray(ResultSet rs) {
    try {
      products.clear();
      while (rs.next()) {
        products.add(new Product(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return products;
  }

  public int rsToId(ResultSet rs){
    try {
      userid = 0;
      while (rs.next()) {
        userid = rs.getInt(1);
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return userid;
  }

  public ArrayList<Product> getProducts(int wishlistId) {
    return rsToArray(load("SELECT name, description, price, url FROM wishlist.products WHERE wishlistid = " + wishlistId + " and type = 2"));
  }

  public void createProduct(String name, String description, String price, String url, int wishlistId) {
    query("insert into wishlist.products(name, description, price, url, type, wishlistId) values(" + "\"" + name + "\", \"" +
        description + "\", \"" + Integer.valueOf(price) + "\", \"" + url + "\", \""+  2 + "\", \"" + wishlistId +  "\")");
  }

  public void deleteProduct(String event, String productName) {
    wishlistId = rsToId(load("select id from wishlist.wishlist where event = '" + event + "'"));
    query("delete from wishlist.products where name = '" + productName + "' AND wishlistid = " + wishlistId);
  }
}
