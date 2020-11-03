package org.patbor.shoprestapi.Repository;

import org.patbor.shoprestapi.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT user_id FROM Users Where email = ?1", nativeQuery = true)
    Integer findUserByEmail(String email);

    @Query(value = "SELECT account_balance FROM users WHERE user_id =?1", nativeQuery = true)
    BigDecimal checkAccountBalance(int user_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET account_balance =?1 WHERE user_id=?2", nativeQuery = true)
    void upgradeAccountBalance(BigDecimal accountBalance, int userID);
}
