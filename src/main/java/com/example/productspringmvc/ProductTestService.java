package com.example.productspringmvc;

import com.example.productspringmvc.model.Product;
import com.example.productspringmvc.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class ProductTestService {
    private ProductService productService;

    public ProductTestService (ProductService productService) {
        this.productService = productService;
    }

    public static void main (String[] args) {
        SpringApplication.run(ProductTestService.class, args);
    }

    @Bean
    CommandLineRunner run () {
        return args -> {
            Product product = Product.builder().title("product 1").designation("desc of product 1").price(1500D).createdAt(new Date()).build();
            Product product1 = Product.builder().title("product 2").designation("desc of product 2").price(1000D).createdAt(new Date()).build();
            Product product2 = Product.builder().title("product 3").designation("desc of product 3").price(2000D).createdAt(new Date()).build();
            Product product3 = Product.builder().title("product 4").designation("desc of product 4").price(3300D).createdAt(new Date()).build();
            System.out.println(this.productService.saveProduct(product, null));
            System.out.println(this.productService.saveProduct(product1, null));
            System.out.println(this.productService.saveProduct(product2, null));
            System.out.println(this.productService.saveProduct(product3, null));
        };
    }
}
