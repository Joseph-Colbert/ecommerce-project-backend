package com.josephcolbert.ecommerce.dao;

import com.josephcolbert.ecommerce.entity.OrderItemOnCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemOnCreditRepository extends JpaRepository <OrderItemOnCredit, Long>{

}
