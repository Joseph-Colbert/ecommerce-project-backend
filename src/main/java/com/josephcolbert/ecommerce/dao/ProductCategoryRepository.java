package com.josephcolbert.ecommerce.dao;

import com.josephcolbert.ecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("https://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "productCategory", path = "product-category")  // Json Entry
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
