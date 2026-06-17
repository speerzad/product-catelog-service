package org.example.productcatelogservice.repos;

import org.example.productcatelogservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepo extends JpaRepository<Category, Long> {
}
