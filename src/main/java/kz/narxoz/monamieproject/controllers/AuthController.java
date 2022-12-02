package kz.narxoz.monamieproject.controllers;

import kz.narxoz.monamieproject.models.Users;
import kz.narxoz.monamieproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {

  @Autowired
  UserService userService;


  @GetMapping("/login")
  @PreAuthorize("isAnonymous()")
  public String login() {
    return "html/login";
  }


  @GetMapping("/logout")
  @PreAuthorize("isAuthenticated()")
  public String logout() {
    return "html/login";
  }



  @GetMapping("/register")
  @PreAuthorize("isAnonymous()")
  public String register(Model model){

    return "html/register";
  }


  @PostMapping("/register")
  @PreAuthorize("isAnonymous()")
  public String toRegister(@RequestParam(name = "user_name")String name,
                           @RequestParam(name = "user_email")String email,
                           @RequestParam(name = "user_password")String password,
                           @RequestParam(name = "re_user_password")String rePassword){

    if(password.equals(rePassword)){

      Users newUser = new Users();
      newUser.setFullName(name);
      newUser.setPassword(password);
      newUser.setEmail(email);

      if(userService.createUser(newUser)!=null){
        return "redirect:/login?success";
      }

    }

    return "redirect:/register?error";
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
