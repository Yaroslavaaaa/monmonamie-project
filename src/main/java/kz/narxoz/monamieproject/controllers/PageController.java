package kz.narxoz.monamieproject.controllers;

import kz.narxoz.monamieproject.entity.Category;
import kz.narxoz.monamieproject.entity.Product;
import kz.narxoz.monamieproject.models.Users;
import kz.narxoz.monamieproject.repositories.CategoryRepository;
import kz.narxoz.monamieproject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("currentUser", getCurrentUser());
        System.out.println(getCurrentUser());
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        return "html/index";
    }



    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable(name = "id") Long id){

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);

        return "html/details";
    }


    @GetMapping("/about")
    public String about(Model model){

        return "html/about";
    }

    @GetMapping("/contacts")
    public String contacts(Model model){

        return "html/contacts";
    }

    @GetMapping("/dostavka")
    public String dostavka(Model model){

        return "html/dostavka";
    }

    @GetMapping("/FAQ")
    public String FAQ(Model model){

        return "html/FAQ";
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
