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
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    //метод возвращающий страницу добавления ного элемента
    @GetMapping("/addprod")
    public String addprod(Model model){
        model.addAttribute("categories", categoryRepository.findAll());

        Product product = new Product();
        model.addAttribute("product", product);
        return "html/addprod";
    }


    //логика добавления нового элемента в таблицу
    @PostMapping("/addproduct")
    public String saveprod(@ModelAttribute("product") Product product,
                           @RequestParam(name = "category_id") Long categoryId, Model model) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            product.setCategory(category);
        }
        productRepository.save(product);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String edit(Model model, @PathVariable(name = "id") Long id) {

        model.addAttribute("currentUser", getCurrentUser());
        model.addAttribute("categories", categoryRepository.findAll());
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);

        return "html/edit";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editSave(@ModelAttribute("product") Product product,
                                @RequestParam(name = "category_id") Long catId, Model model) {

        Category category = categoryRepository.findById(catId).orElse(null);
            product.setCategory(category);
            productRepository.save(product);

        return "redirect:/";
    }
//    @PostMapping("/save")
//    public String editSave(@ModelAttribute("product") Product product,
//                           @RequestParam(name = "category_id") Long categoryId,
//                           @RequestParam(name = "prod_id") Long prodId,
//                                 Model model) {
//        Product existingProduct = productRepository.findById(prodId).orElse(null);
//        Category category = categoryRepository.findById(categoryId).orElse(null);
////        existingProduct.setId(product.getId());
//        existingProduct.setName(product.getName());
//        existingProduct.setDescription(product.getDescription());
//        existingProduct.setCategory(category);
//        existingProduct.setBrand(product.getBrand());
//        existingProduct.setPrice(product.getPrice());
//        existingProduct.setImage(product.getImage());
//        if (category != null) {
//            product.setCategory(category);
//        }
//        productRepository.save(product);
//        return "redirect:/";
//    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id, Model model) {

        productRepository.deleteById(id);

        return "redirect:/";
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
