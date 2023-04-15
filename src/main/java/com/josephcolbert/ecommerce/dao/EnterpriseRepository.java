package com.josephcolbert.ecommerce.dao;

import com.josephcolbert.ecommerce.entity.Enterprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {  //(entity, primary key){


}
