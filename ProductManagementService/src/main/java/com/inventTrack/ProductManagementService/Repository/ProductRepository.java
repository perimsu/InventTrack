package com.inventTrack.ProductManagementService.Repository;

import com.inventTrack.ProductManagementService.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
