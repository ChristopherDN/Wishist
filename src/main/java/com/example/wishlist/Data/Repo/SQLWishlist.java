package com.example.wishlist.Data.Repo;

import com.example.wishlist.Data.Utility.DBManager;
import com.example.wishlist.Domain.Models.User;
import com.example.wishlist.Domain.Models.Wishlist;
import org.springframework.stereotype.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Controller
public class SQLWishlist {
  Connection connection;
  PreparedStatement ps;
  boolean bol;
  ResultSet rs;
  ArrayList<Wishlist> wishlists = new ArrayList<>();
  int userid;

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

  public ArrayList<Wishlist> getResults(ResultSet rs) {
    try {
      wishlists.clear();
      while (rs.next()) {
        wishlists.add(new Wishlist(rs.getString(1)));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return wishlists;
  }


  public int getId(ResultSet rs){
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


  public void createWishlist(User user, String event) {
    userid = getId(load("select id from wishlist.users where name = '" + user.getName() + "'"));
    query("insert into wishlist.wishlist(event, userid) values('"+ event + "' , '" + userid + "')");
  }

  //TODO Skal bruges af andre statements, til at give wishlistId
  public int getWishlistId(User user, String event) {
    return getId(load("select id from wishlist.wishlist where userid =" + getId(load("select id from wishlist.users where name = '" + user.getName())) + " and event ='" + event + "'"));
  }

  public void deleteWishlist(User user, String event) {
    query("delete from wishlist.wishlist where event = '" + event + "' and userid = " + getId(load("select id from wishlist.users where name = '" + user.getName() + "'")));
  }

  public ArrayList<Wishlist> getWishlists(User user) {
    return getResults(load("select event from wishlist.wishlist where userid =" + getId(load("select id from wishlist.users where name = '" + user.getName() + "'"))));
  }

  public int setWishlistId(User user, String event) {
    userid = getId(load("select id from wishlist.users where name = '" + user.getName() + "'"));
    return getId(load("select id from wishlist.wishlist where userid =" + userid +  " and event ='" + event + "'"));
  }
}


