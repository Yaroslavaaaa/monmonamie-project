package kz.narxoz.monamieproject.controllers;


import kz.narxoz.monamieproject.observer.listeners.SubscribeActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SubscribeController {

  SubscribeActions subscribeActions;

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam(name = "email")String email){
      subscribeActions.subscribe(email);
      return "subscribed";
    }


}
