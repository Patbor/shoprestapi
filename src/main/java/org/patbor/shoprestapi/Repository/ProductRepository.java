package org.patbor.shoprestapi.Repository;

import org.patbor.shoprestapi.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
@Query(value = "SELECT * FROM Product WHERE name = ?1", nativeQuery = true)
    Product findProductByName(String name);



}
