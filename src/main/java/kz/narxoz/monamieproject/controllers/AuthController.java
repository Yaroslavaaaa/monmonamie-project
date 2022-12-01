package kz.narxoz.monamieproject.controllers;

import kz.narxoz.monamieproject.models.Users;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthController {


  @GetMapping("/login")
  @PreAuthorize("isAnonymous()")
  public String login() {
    return "html/login";
  }


  @GetMapping("/logout")
  public String logout() {
    return "html/login";
  }



  private Users getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      Users currentUser = (Users) authentication.getPrincipal();
      return  currentUser;
    }
    return null;
  }
}
