package org.example.productcatelogservice.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.productcatelogservice.dtos.CategoryDto;
import org.example.productcatelogservice.dtos.ProductDto;
import org.example.productcatelogservice.models.Category;
import org.example.productcatelogservice.models.Product;
import org.example.productcatelogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping("/products")
    public List<ProductDto> listProduct() {
        List<Product> products = productService.getAllProducts();
        if (products == null) {
            return new ArrayList<>();
        }
        return products.stream()
                .map(this::from)
                .toList();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductByID(@PathVariable Long id) {
        if (id <= 0L) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = productService.getProductById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(from(product),HttpStatus.OK);
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
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto input) {
        Product response = productService.createProduct(to(input));
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(from(response),HttpStatus.CREATED);
    }

    // add PUT / PATCH / DELETE

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable Long id, @RequestBody ProductDto input) {
        if (id <= 0L) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product response = productService.replaceProduct(id, to(input));
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // update the product with input data and save it
        // for demo, we just return the input as output
        return new ResponseEntity<>(from(response), HttpStatus.OK);
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto input) {
        if (id <= 0L) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product response = productService.updateProduct(id, to(input));
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // update the product with input data and save it
        // for demo, we just return the input as output
        return new ResponseEntity<>(from(response), HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
        if (id <= 0L) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product response = productService.deleteProduct(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(from(response), HttpStatus.OK);
    }


    private ProductDto from(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        if (product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }

    private Product to(ProductDto input) {
        Product product = new Product();
        product.setId(input.getId());
        product.setName(input.getName());
        product.setDescription(input.getDescription());
        product.setPrice(input.getPrice());
        if (input.getCategory() != null) {
            CategoryDto categoryDto = input.getCategory();
            Category category = new Category();
            category.setId(categoryDto.getId());
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());
            product.setCategory(category);
        }
        return product;
    }
}
