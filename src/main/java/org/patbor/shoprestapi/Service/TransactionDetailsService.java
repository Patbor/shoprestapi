package org.patbor.shoprestapi.Service;

import org.patbor.shoprestapi.Entity.TransactionDetail;
import org.patbor.shoprestapi.Repository.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDetailsService {

    private TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    public TransactionDetailsService(TransactionDetailsRepository transactionDetailsRepository) {
        this.transactionDetailsRepository = transactionDetailsRepository;
    }

    public void addTransactionDetail(TransactionDetail transactionDetail) {
        if(transactionDetail != null)
            transactionDetailsRepository.save(transactionDetail);
    }
}
