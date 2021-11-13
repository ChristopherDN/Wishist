package com.example.wishlist.Controllers;

import com.example.wishlist.Domain.Models.User;
import com.example.wishlist.Domain.Services.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpSession;


@Controller
public class WishlistController {
  WishlistService wishlistService = new WishlistService();
  User user;

  @GetMapping("/frontpage")
  public String showWishlists(Model model, HttpSession session) {
    user = (User) session.getAttribute("user");
    model.addAttribute("loopwishlists", wishlistService.getWishlists(user));
    return "frontpage";
  }

  @PostMapping("/createWishlist")
  public String createWishlist(WebRequest request, HttpSession session) {
    String event = request.getParameter("event");
    user = (User) session.getAttribute("user");
    wishlistService.createWishlist(user, event);
    return "redirect:/frontpage";
  }

  @GetMapping("/removeWishlist/{id}")
  public String deleteWishList(@PathVariable(value = "id") String id) {
    wishlistService.deleteWishlist(user, id);
    return "redirect:/frontpage";
  }
}
