package com.josephcolbert.ecommerce.dao;


import com.josephcolbert.ecommerce.entity.EnterpriseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;


public interface EnterpriseCategoryRepository extends JpaRepository<EnterpriseCategory, Long> {
}
