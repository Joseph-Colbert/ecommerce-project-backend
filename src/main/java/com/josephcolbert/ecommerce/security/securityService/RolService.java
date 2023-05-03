package com.josephcolbert.ecommerce.security.securityService;

import com.josephcolbert.ecommerce.security.securityEntity.Rol;
import com.josephcolbert.ecommerce.security.securityEnums.RolName;
import com.josephcolbert.ecommerce.security.securityRepository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolName(RolName rolName) {
        return rolRepository.findByRolName(rolName);
    }
    public void save(Rol rol) {
        rolRepository.save(rol);
    }

}
