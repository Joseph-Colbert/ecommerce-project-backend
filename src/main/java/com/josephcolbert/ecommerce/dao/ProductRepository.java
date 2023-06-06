package com.josephcolbert.ecommerce.dao;

import com.josephcolbert.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@RepositoryRestResource
@CrossOrigin("https://localhost:4200")
public interface ProductRepository extends JpaRepository<Product, Long> {  //(entity, primary key)
    Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable); //select * from products where category_id=?..... simula ese query

    Page<Product> findByNameContaining(@Param("name") String name, Pageable page);// select *from Products p where p.name like concat('%', :name, '%')

    Page<Product> findByEnterpriseId(@Param("enterpriseId") String enterpriseId, Pageable page);// select *from Products p where p.name like concat('%', :name, '%')

    boolean existsById(Long id);

}
