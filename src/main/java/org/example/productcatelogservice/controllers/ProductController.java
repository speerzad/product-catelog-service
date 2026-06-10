package org.example.productcatelogservice.controllers;

import org.example.productcatelogservice.dtos.ProductDto;
import org.example.productcatelogservice.models.Category;
import org.example.productcatelogservice.models.Product;
import org.example.productcatelogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/product")
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping("/products")
    public List<Product> listProduct() {
        List<Product> products = new ArrayList<>();
        Product iPhone = new Product();
        iPhone.setName("iPhone");
        products.add(iPhone);
        Product Android = new Product();
        Android.setName("Android");
        products.add(Android);
        return products;
    }

    @GetMapping("/products/{id}")
    public Product getProductByID(@PathVariable Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("iPhone");
        return product;
    }

    @GetMapping("/products/{catId}/{prodId}")
    public Product GetProduct(
            @PathVariable("catId") Long catId,
            @PathVariable("prodId") Long prodId){
        Product product = new Product();
        product.setId(prodId);
        product.setName("iPhone");
        Category category = new Category();
        category.setId(catId);
        category.setName("Mobile");
        product.setCategory(category);
        return product;
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto input) {
        return input;
    }

    // add PUT / PATCH / DELETE
}
