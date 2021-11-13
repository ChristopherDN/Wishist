package com.example.wishlist.Controllers;

import com.example.wishlist.Domain.Models.TempProduct;
import com.example.wishlist.Domain.Models.User;
import com.example.wishlist.Domain.Services.TempProductService;
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
  TempProductService tempProductService = new TempProductService();
  ArrayList<TempProduct> listOfProducts = new ArrayList();
  WishlistService wishlistService = new WishlistService();
  User user;
  String event;

  @GetMapping("/addproduct")
  public String addproduct(Model model) {
    if (user == null){
      return "login";
    }
    model.addAttribute("loopproduct", listOfProducts);
    return "addproduct";
  }

  @GetMapping("/editWishlist/{id}")
  public String editWishlist(@PathVariable(value = "id") String id, HttpSession session, Model model) {
    event = id;
    user = (User)session.getAttribute("user");
    wishlistService.setWishlistId(user, event);
    listOfProducts = tempProductService.getProductsWishlist(user, event);
    model.addAttribute("loopproduct", listOfProducts);
    return "redirect:/addproduct";
  }

  /*//TODO Slettes?
  @GetMapping("/topProduct")
  public String topproduct(Model model) {
    for (int i = 0; i < listOfProducts.size(); i++) {
      System.out.println("Min arrayliste:");
      System.out.println(listOfProducts.get(i).toString());
    }
    model.addAttribute("loopproduct", listOfProducts);
    return "topProducts";
  }*/

  @PostMapping("/addproduct")
  public String addDataToProduct(WebRequest request, Model model, HttpSession session) {
    tempProductService.createProduct(request.getParameter("name"),
        request.getParameter("description"), request.getParameter("price"),
        request.getParameter("url"), (User) session.getAttribute("user"), event);
    listOfProducts = tempProductService.getProductsWishlist(user, event);
    model.addAttribute("loopproduct", listOfProducts);
    return "redirect:/addproduct";
  }


  //TODO HVad gør den?
  @GetMapping("/wishlist")
  public String showWishlist(Model model) {
    model.addAttribute("loopproduct", listOfProducts);
    return "wishlist";
  }

  @GetMapping("/remove/{id}")
  public String deleteProduct(@PathVariable(value = "id") String id, Model model) {
    tempProductService.deleteProduct(event, id);
    listOfProducts = tempProductService.getProductsWishlist(user, event);
    model.addAttribute("loopproduct", listOfProducts);
    return "redirect:/addproduct";
  }

  @GetMapping("/shareWishlist/{id}")
  public String editWishlist(Model model, HttpSession session, HttpServletRequest httpServletRequest) {
    session.invalidate();
    model.addAttribute("loop", listOfProducts);
    return "finalWishlist";
  }
}
