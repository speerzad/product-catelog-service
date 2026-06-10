package org.example.productcatelogservice.services;

import org.example.productcatelogservice.dtos.ProductDto;
import org.example.productcatelogservice.models.Product;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IProductService {
    Product getProductById(Long productID);
    List<Product> getAllProducts();
    Product createProduct(Product input);
}
