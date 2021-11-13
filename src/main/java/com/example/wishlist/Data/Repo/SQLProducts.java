package com.example.wishlist.Data.Repo;

import com.example.wishlist.Data.Utility.DBManager;
import com.example.wishlist.Domain.Models.TempProduct;
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
  ArrayList<TempProduct> tempProducts = new ArrayList<>();
  int userid;
  int wishlistId;


  //Skal denne laves om til at run() functions?
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
  //Skal ændres så den ikke sender en sql statement, men laver et run() function i stedet.
  public ResultSet load(String sqlCommand)  {
    try {
      connection = DBManager.getConnection();
      ps = connection.prepareStatement(sqlCommand);
      rs = ps.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      //throw new ExceptionService(ex.getMessage());
    }
    return rs;
  }

  public ArrayList<TempProduct> getResults(ResultSet rs) {
    try {
      tempProducts.clear();
      while (rs.next()) {
        tempProducts.add(new TempProduct(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return tempProducts;
  }

  public int getWishlistId(ResultSet rs){
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

  public ArrayList<TempProduct> getProductsWishlist(int wishlistId) {
    return getResults(load("SELECT name, description, price, url FROM wishlist.products WHERE wishlistid = " + wishlistId + " and type = 2"));
  }

  public void createProduct(String name, String description, String price, String url, int wishlistId) {
    query("insert into wishlist.products(name, description, price, url, type, wishlistId) values(" + "\"" + name + "\", \"" +
        description + "\", \"" + Integer.valueOf(price) + "\", \"" + url + "\", \""+  2 + "\", \"" + wishlistId +  "\")");
  }

  public void deleteProduct(String event, String productName) {
    wishlistId = getWishlistId(load("select id from wishlist.wishlist where event = '" + event + "'"));
    query("delete from wishlist.products where name = '" + productName + "' AND wishlistid = " + wishlistId);
  }
}
