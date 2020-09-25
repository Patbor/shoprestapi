package org.patbor.shoprestapi.Repository;

import org.patbor.shoprestapi.Entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetail, Integer> {
}
