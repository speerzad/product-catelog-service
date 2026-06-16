package org.example.productcatelogservice.services;

import org.example.productcatelogservice.dtos.FakeStoreProductDto;
import org.example.productcatelogservice.models.Category;
import org.example.productcatelogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    private RestTemplate restTemplate;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Override
    public Product getProductById(Long id) {
        restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, id);
        return from(fakeStoreProductDto);
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(Product input) {
        return null;
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        if (fakeStoreProductDto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
