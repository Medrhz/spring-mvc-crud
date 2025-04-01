package com.example.productspringmvc.service;

import com.example.productspringmvc.model.Product;
import com.example.productspringmvc.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service

public class ProductService {
    private ProductRepository productRepository;
    private UploadFileService uploadFileService;

    public ProductService (ProductRepository productRepository, UploadFileService uploadFileService) {
        this.productRepository = productRepository;
        this.uploadFileService = uploadFileService;
    }


    // getAll
    public List<Product> getAll () {
        return this.productRepository.findAll();
    }

    //get
    public Product get (Long id) {
        return this.productRepository.findById(id).orElse(null);
    }

    //save
    public Product saveProduct (Product product, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String imagePath = this.uploadFileService.saveFile(file, "product");
            product.setImage(imagePath);
        } else {
            product.setImage(null);
        }
        return this.productRepository.save(product);
    }

    //saveAll
    public List<Product> saveAllProducts (List<Product> productList) {
        return this.productRepository.saveAll(productList);
    }

    //update
    public Product updateProduct (Product product, MultipartFile file) throws IOException {
        Product existingProduct = this.get(product.getId());
        if (file.isEmpty()) {
            // Keep the old image
            product.setImage(existingProduct.getImage());
        } else {
            // Save new image and update the product
            String imagePath = this.uploadFileService.saveFile(file, "product");
            product.setImage(imagePath);
        }
        return this.productRepository.save(product);
    }

    //delete
    public Product deleteProduct (Long id) {

        Product product = this.get(id);

        if (product != null) {
            this.productRepository.delete(product);
            return product;
        } else {

            return null;
        }

    }

}
