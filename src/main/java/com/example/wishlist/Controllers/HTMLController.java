package com.example.wishlist.Controllers;

import com.example.wishlist.Domain.Models.User;
import com.example.wishlist.Domain.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HTMLController {
  UserService userService = new UserService();
  User user;

  @GetMapping("/")
  public String index(HttpSession session) {
    if (session.getAttribute("user")!= null){
      return "redirect:/frontpage";
    }
    return "index";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/createUser")
  public String createUser() {
    return "createUser";
  }

  @PostMapping("/register")
  public String register(WebRequest request) {
    userService.registerUser(
        request.getParameter("name"),
        request.getParameter("email"),
        request.getParameter("password"));
    return "login";
  }

  @PostMapping("/frontpage")
  public String validateLogin(WebRequest request, HttpSession session, Model model) {
    user = userService.validateLogin(
        request.getParameter("user"),
        request.getParameter("password"));

    //Set Session to user, validate user is not null.
    if (session.getAttribute("user") == null) {
      if (user != null) {
        model.addAttribute("user", user);
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
        return "redirect:/frontpage";
      }
    }
    return "login";
  }

  @GetMapping("/logout")
  public String logOut(HttpSession session) {
    session.invalidate();
    user = null;
    return "redirect:/";
  }
  //these are just endpoints ready to be used

  @GetMapping("/contact")
  public String contact() {
    return "contact";
  }
}



