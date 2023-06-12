package com.josephcolbert.ecommerce.dao;

import com.josephcolbert.ecommerce.entity.OrderItemOnCredit;
import com.josephcolbert.ecommerce.entity.OrderOnCredit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("https://localhost:4200")
@RepositoryRestResource
public interface DebtsItemRepository extends JpaRepository<OrderItemOnCredit,Long> {
    Page<OrderItemOnCredit> findAllByOrderOnCreditOrderTrackingNumber(@Param("orderTrackingNumber") String orderTrackingNumber, Pageable pageable);
}