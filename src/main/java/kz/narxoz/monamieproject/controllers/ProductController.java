package kz.narxoz.monamieproject.controllers;


import kz.narxoz.monamieproject.entity.Category;
import kz.narxoz.monamieproject.entity.Product;
import kz.narxoz.monamieproject.models.Users;
import kz.narxoz.monamieproject.repositories.CategoryRepository;
import kz.narxoz.monamieproject.repositories.ProductRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Value("${file.images.viewPath}")
    private String viewPath;

    @Value("${file.images.uploadPath}")
    private String uploadPath;

    //метод возвращающий страницу добавления ного элемента
    @GetMapping("/addprod")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public String addprod(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        return "html/addprod";
    }


    //логика добавления нового элемента в таблицу
    @PostMapping("/addproduct")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public String saveprod(
       @RequestParam(name = "name") String name,
        @RequestParam(name = "price") int price,
        @RequestParam(name = "description") String description,
        @RequestParam(name = "brand") String brand,
        @RequestParam(name = "image") MultipartFile image,
        @RequestParam(name = "category_id") Long categoryId, Model model) {

        Product product = new Product();

        if(image.getContentType().equals("image/jpeg") || image.getContentType().equals("image/jpg")){
            try{

                String picName = DigestUtils.sha1Hex("image"+name+"Picture");

                byte []bytes = image.getBytes();
                Path path = Paths.get(uploadPath+picName+".jpg");
                Files.write(path, bytes);

                product.setImage(picName);

                Category category = categoryRepository.findById(categoryId).orElse(null);
                if (category != null) {
                    product.setCategory(category);
                }

                product.setName(name);
                product.setPrice(price);
                product.setDescription(description);
                product.setBrand(brand);



                productRepository.save(product);

                return "redirect:/";

            }catch (Exception e){
                e.printStackTrace();
            }
        }


        return "redirect:/addprod?error";
    }


    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public String edit(Model model, @PathVariable(name = "id") Long id){

        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "html/edit";


    }


    @PostMapping("/save/{id}")
    public String editSave(Model model,
                           @PathVariable(name = "id") Long id,
                           @RequestParam(name = "name") String name,
                           @RequestParam(name = "price") int price,
                           @RequestParam(name = "description") String description,
                           @RequestParam(name = "image") MultipartFile image,
                           @RequestParam(name = "brand") String brand){

        Product product = productRepository.findById(id).orElse(null);


        if(image.getContentType().equals("image/jpeg") || image.getContentType().equals("image/jpg")){
            try{

                String picName = DigestUtils.sha1Hex("image"+name+"Picture");

                byte []bytes = image.getBytes();
                Path path = Paths.get(uploadPath+picName+".jpg");
                Files.write(path, bytes);

                product.setImage(picName);


                product.setName(name);
                product.setPrice(price);
                product.setDescription(description);
                product.setBrand(brand);



                productRepository.save(product);

                return "redirect:/";

            }catch (Exception e){
                e.printStackTrace();
            }
        }


        return "redirect:/";

    }


    @GetMapping(value = "/img/{url}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public @ResponseBody byte[] viewImage(@PathVariable(name = "url") String url) throws IOException {

        String imageUrl = viewPath+url+".jpg";

        InputStream in;

        try{

            ClassPathResource resource = new ClassPathResource(imageUrl);
            in = resource.getInputStream();


        }catch (Exception e){
            ClassPathResource resource = new ClassPathResource(imageUrl);
            in = resource.getInputStream();
            e.printStackTrace();
        }

        return IOUtils.toByteArray(in);

    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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