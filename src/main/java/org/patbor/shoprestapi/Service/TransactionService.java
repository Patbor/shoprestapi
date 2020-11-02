package org.patbor.shoprestapi.Service;


import org.patbor.shoprestapi.Entity.Transaction;
import org.patbor.shoprestapi.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> findAllTransaction() {
        return transactionRepository.findAll().stream()
                .sorted(Comparator.comparing(Transaction::getDate))
                .collect(Collectors.toList());
    }

    public List<Transaction> findAllTransactionByDate(LocalDate from, LocalDate to) {
        return transactionRepository.findAll()
                .stream()
                .filter(data -> data.getDate().isBefore(to) && data.getDate().isAfter(from))
                .collect(Collectors.toList());
    }

    public List<Transaction> findAllTransactionByDate(LocalDate from) {
        return transactionRepository.findAll()
                .stream()
                .filter(data -> data.getDate().isBefore(from))
                .collect(Collectors.toList());
    }

}
