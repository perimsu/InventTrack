package com.inventTrack.ProductManagementService.Repository;

import com.inventTrack.ProductManagementService.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String newProduct);
}
