package org.patbor.shoprestapi.Repository;

import org.patbor.shoprestapi.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM products WHERE name = ?1", nativeQuery = true)
    Product findProductByName(String name);

    @Query(value = "SELECT available_count FROM products WHERE name =?1", nativeQuery = true)
    int getAmount(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE products SET available_count =?1 WHERE name =?2", nativeQuery = true)
    void updateAmount(int amount, String name);


}
