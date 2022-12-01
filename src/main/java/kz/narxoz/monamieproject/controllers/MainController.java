package kz.narxoz.monamieproject.controllers;


import kz.narxoz.monamieproject.entity.Product;
import kz.narxoz.monamieproject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    ProductRepository productRepository;

//    @GetMapping("/")
//    public String index(Model model){
////        List<Product> products = productRepository.findAll();
////        model.addAttribute("products", products);
//        return "html/index";
//    }



}
