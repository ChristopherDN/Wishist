package com.example.wishlist.Controllers;

import com.example.wishlist.Domain.Models.Product;
import com.example.wishlist.Domain.Models.User;
import com.example.wishlist.Domain.Services.ProductService;
import com.example.wishlist.Domain.Services.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ProductsController {
  ProductService productService = new ProductService();
  ArrayList<Product> products = new ArrayList();
  WishlistService wishlistService = new WishlistService();
  User user;
  String event;

  @GetMapping("/addproduct")
  public String addProduct(Model model) {
    if (user == null){
      return "login";
    }
    model.addAttribute("loopproduct", products);
    return "addproduct";
  }

  @GetMapping("/editWishlist/{id}")
  public String shareWishlist(@PathVariable(value = "id") String id, HttpSession session, Model model) {
    event = id;
    user = (User)session.getAttribute("user");
    wishlistService.setWishlistId(user, event);
    products = productService.getProducts(user, event);
    model.addAttribute("loopproduct", products);
    return "redirect:/addproduct";
  }

  @PostMapping("/addproduct")
  public String addDataProduct(WebRequest request, Model model, HttpSession session) {
    productService.createProduct(
        request.getParameter("name"),
        request.getParameter("description"), request.getParameter("price"),
        request.getParameter("url"), (User) session.getAttribute("user"), event);
    products = productService.getProducts(user, event);
    model.addAttribute("loopproduct", products);
    return "redirect:/addproduct";
  }

  @GetMapping("/wishlist")
  public String showWishlist(Model model) {
    model.addAttribute("loopproduct", products);
    return "wishlist";
  }

  @GetMapping("/remove/{id}")
  public String deleteProduct(@PathVariable(value = "id") String id, Model model) {
    productService.deleteProduct(event, id);
    products = productService.getProducts(user, event);
    model.addAttribute("loopproduct", products);
    return "redirect:/addproduct";
  }

  @GetMapping("/shareWishlist/{id}")
  public String shareWishlist(Model model, HttpSession session) {
    session.invalidate();
    model.addAttribute("loop", products);
    return "finalWishlist";
  }
}
