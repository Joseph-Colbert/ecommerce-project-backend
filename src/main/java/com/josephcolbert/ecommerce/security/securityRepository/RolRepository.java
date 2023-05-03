package com.josephcolbert.ecommerce.security.securityRepository;

import com.josephcolbert.ecommerce.security.securityEntity.Rol;
import com.josephcolbert.ecommerce.security.securityEnums.RolName;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol>findByRolName(RolName rolName);
}
