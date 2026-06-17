package org.example.productcatelogservice.services;

import org.example.productcatelogservice.dtos.FakeStoreProductDto;
import org.example.productcatelogservice.models.Category;
import org.example.productcatelogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Stream;

@Service
public class FakeStoreProductService implements IProductService {

    private RestTemplate restTemplate;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    private <T> ResponseEntity<T> requestForEntity(String url, HttpMethod method, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, method, requestCallback, responseExtractor, uriVariables);
    }

    @Override
    public Product getProductById(Long id) {
        restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = requestForEntity(
                "https://fakestoreapi.com/products/{id}", HttpMethod.GET, null, FakeStoreProductDto.class, id);
        if (isValidFakeStoreDtoResponse(response)) {
            return from(response.getBody());
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> response = requestForEntity(
                "https://fakestoreapi.com/products", HttpMethod.GET, null, FakeStoreProductDto[].class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            FakeStoreProductDto[] fakeStoreProductDtos = response.getBody();
            return Stream.of(fakeStoreProductDtos)
                    .map(this::from)
                    .toList();
        }
        return List.of();
    }

    @Override
    public Product createProduct(Product input) {
        restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = requestForEntity(
                "https://fakestoreapi.com/products", HttpMethod.POST, to(input), FakeStoreProductDto.class);
        if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
            return from(response.getBody());
        }
        return null;
    }

    @Override
    public Product replaceProduct(Long productID, Product input) {
        restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = requestForEntity(
                "https://fakestoreapi.com/products/{id}", HttpMethod.PUT, to(input), FakeStoreProductDto.class, productID);
        if (isValidFakeStoreDtoResponse(response)) {
            return from(response.getBody());
        }
        return null;
    }

    @Override
    public Product updateProduct(Long productID, Product input) {
        restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = requestForEntity(
                "https://fakestoreapi.com/products/{id}", HttpMethod.PATCH, to(input), FakeStoreProductDto.class, productID);
        if (isValidFakeStoreDtoResponse(response)) {
            return from(response.getBody());
        }
        return null;
    }

    @Override
    public Product deleteProduct(Long productID) {
        restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = requestForEntity(
                "https://fakestoreapi.com/products/{id}", HttpMethod.DELETE, null, FakeStoreProductDto.class, productID);
        if (isValidFakeStoreDtoResponse(response)) {
            return from(response.getBody());
        }
        return null;
    }

    private boolean isValidFakeStoreDtoResponse(ResponseEntity<FakeStoreProductDto> response) {
        return response.getStatusCode() == HttpStatus.OK && response.getBody() != null;
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

    private FakeStoreProductDto to(Product product) {
        if (product == null) {
            return null;
        }
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        Category category = product.getCategory();
        if (category != null) {
            fakeStoreProductDto.setCategory(category.getName());
        }
        return fakeStoreProductDto;
    }
}
