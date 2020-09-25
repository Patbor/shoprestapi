package org.patbor.shoprestapi.Repository;

import org.patbor.shoprestapi.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

}
