package com.josephcolbert.ecommerce.dao;

import com.josephcolbert.ecommerce.entity.Enterprise;
import com.josephcolbert.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("https://localhost:4200")
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {  //(entity, primary key){
    Page<Enterprise> findByCategoryEId(@Param("id") Long id, Pageable pageable);

}
