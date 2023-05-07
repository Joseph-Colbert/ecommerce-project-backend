package com.josephcolbert.ecommerce.service;

//import com.josephcolbert.ecommerce.dao.ProductDao;
import com.josephcolbert.ecommerce.dao.ProductRepository;
import com.josephcolbert.ecommerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    ProductRepository productDao;

    public Optional<Product> getOne(int id) {
        return productDao.findById((long) id);
    }

   /* public Optional<Product> getByName(String name) {
        return productDao.findByName(name);
    }*/

    public void save(Product product) {
        productDao.save(product);
    }

    public void delete(int id) {
        productDao.deleteById((long) id);
    }

    public boolean existById(int id) {
        return productDao.existsById((long) id);
    }

  /* public boolean existByName(String name) {
        return productDao.existsByName(name);
    }
*/
}
