package kz.monamieproject.monamie.controllers;


import kz.narxoz.monamieproject.models.Users;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {




  @GetMapping("/profile")
  @PreAuthorize("isAuthenticated()")
  public String profile(Model model) {
    model.addAttribute("currentUser", getCurrentUser());
    return "html/profile";
  }


  @GetMapping("/edit_profile")
  @PreAuthorize("isAuthenticated()")
  public String editProfile(Model model) {
    model.addAttribute("currentUser", getCurrentUser());
    return "html/edit_profile";
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
