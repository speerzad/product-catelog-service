package org.example.productcatelogservice.repos;

import org.example.productcatelogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepo extends JpaRepository<Product, Long> {
}
