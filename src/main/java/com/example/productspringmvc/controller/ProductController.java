package com.example.productspringmvc.controller;

import com.example.productspringmvc.model.Product;
import com.example.productspringmvc.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController (ProductService productService) {
        this.productService = productService;
    }

    // homeView
    @GetMapping(name = "home", value = "/")
    public String homeAction (Model model) {
        List<Product> productList = this.productService.getAll();
        model.addAttribute("products", productList);
        return "index";
    }

    // listView
    @RequestMapping(name = "list", value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String listAction (Model model) {
        List<Product> productList = this.productService.getAll();
        model.addAttribute("products", productList);
        return "productList";
    }

    // createView
    @RequestMapping(name = "create", value = "/create", method = {RequestMethod.GET, RequestMethod.POST})
    public String createAction (Model model) {
        model.addAttribute("product", new Product());
        return "create";
    }

    // saveAction
    @RequestMapping(name = "save", value = "/save", method = {RequestMethod.POST})
    public String saveAction (@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile file) {
        try {
            product.setCreatedAt(new Date());
            Product productSave = this.productService.saveProduct(product, file);
            if (productSave != null) {
                return "redirect:/product/";
            } else {
                return "create";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/product/create";
        }

    }

    // editView
    @GetMapping(name = "editView", value = "/edit/{id}")
    public String editView (@PathVariable Long id, Model model) {
        System.out.println(id);
        model.addAttribute("product", this.productService.get(id));
        return "edit";
    }

    // updateAction
    @PostMapping(name = "editAction", value = "/update")
    public String editAction (@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile file) {
        try {
            this.productService.updateProduct(product, file);
            return "redirect:/product/";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/product/create";
        }
    }

    // deleteAction
    @GetMapping(name = "editView", value = "/delete/{id}")
    public String deleteAction (@PathVariable Long id) {
        this.productService.deleteProduct(id);
        return "redirect:/product/";
    }

}
