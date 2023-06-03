package com.josephcolbert.ecommerce.dao;

import com.josephcolbert.ecommerce.entity.OrderOnCredit;
import com.josephcolbert.ecommerce.entity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("https://localhost:4200")
@RepositoryRestResource
public interface PromotionRepository {

   // Page<Promotion> findByPromotionName(@Param("name") String name, Pageable pageable);
}
