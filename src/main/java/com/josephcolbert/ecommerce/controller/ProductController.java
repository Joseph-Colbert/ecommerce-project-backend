package com.josephcolbert.ecommerce.controller;

import com.josephcolbert.ecommerce.dto.ProductDto;
import com.josephcolbert.ecommerce.entity.Product;
import com.josephcolbert.ecommerce.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto) {
        // Verificar si el producto existe
        Optional<Product> optionalProduct = productService.getOne(Math.toIntExact(id));
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Validar los datos recibidos en productDto, por ejemplo:
       // if (StringUtils.isBlank(productDto.getName()) || productDto.getUnitPrice() == null) {
        //    return ResponseEntity.badRequest().body("Nombre y Precio Unitario son obligatorios.");
     //   }

        // Obtener el producto existente
        Product product = optionalProduct.get();

        // Actualizar los campos del producto con los datos proporcionados en productDto
        if (productDto.getSku() != null) {
            product.setSku(productDto.getSku());
        }
        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getDescription() != null) {
            product.setDescription(productDto.getDescription());
        }
        if (productDto.getUnitPrice() != null) {
            product.setUnitPrice(productDto.getUnitPrice());
        }
        if (productDto.getImageUrl() != null) {
            product.setImageUrl(productDto.getImageUrl());
        }
        if (productDto.getUnitsInStock() != null) {
            product.setUnitsInStock(productDto.getUnitsInStock());
        }
        if (productDto.getLastUpdated() != null) {
            product.setLastUpdated(productDto.getLastUpdated());
        }
        product.setLastUpdated(new Date());
        // Actualizar otros campos según sea necesario

        // Guardar el producto actualizado en la base de datos
        productService.save(product);

        return ResponseEntity.ok("Producto actualizado exitosamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        // Verificar si el producto existe
        if (!productService.existById(Math.toIntExact(id))) {
            return ResponseEntity.notFound().build();
        }

        // Eliminar el producto
        productService.delete(id);

        return ResponseEntity.ok("Producto eliminado exitosamente.");
    }

}
