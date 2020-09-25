package org.patbor.shoprestapi.Repository;

import org.patbor.shoprestapi.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT user_id FROM Users Where email = ?1", nativeQuery = true)
    Integer findUserByEmail(String email);
}
