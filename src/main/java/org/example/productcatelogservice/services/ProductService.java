package org.example.productcatelogservice.services;

import org.example.productcatelogservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {
    @Override
    public Product getProductById(Long productID) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(Product input) {
        return null;
    }
}
