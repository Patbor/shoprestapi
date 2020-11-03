package org.patbor.shoprestapi.Service;

import org.patbor.shoprestapi.Entity.Product;
import org.patbor.shoprestapi.Entity.Transaction;
import org.patbor.shoprestapi.Entity.TransactionDetail;
import org.patbor.shoprestapi.Exceptions.MoneyException;
import org.patbor.shoprestapi.Exceptions.NotFoundException;
import org.patbor.shoprestapi.POJO.Order;
import org.patbor.shoprestapi.POJO.Value;
import org.patbor.shoprestapi.Repository.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionDetailsService {

    private TransactionDetailsRepository transactionDetailsRepository;
    private TransactionService transactionService;
    private UserService userService;

    @Autowired
    public TransactionDetailsService(TransactionDetailsRepository transactionDetailsRepository,
                                     TransactionService transactionService,
                                     UserService userService) {
        this.transactionDetailsRepository = transactionDetailsRepository;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    private void addTransactionDetail(TransactionDetail transactionDetail) {
        if (transactionDetail != null)
            transactionDetailsRepository.save(transactionDetail);
    }

    public Value getFinalPriceOfOneTransaction(List<Order> ordersOnBasket) {
        Value value = new Value();
        for (Order order : ordersOnBasket) {
            BigDecimal actualValueBrutto = value.getValueBrutto();
            BigDecimal actualValueNetto = value.getValueNetto();
            int amount = order.getAmount();
            if (actualValueBrutto != null && actualValueNetto != null) {
                value.setValueBrutto(actualValueBrutto
                        .add((order.getProduct().getValueBrutto())
                                .multiply(BigDecimal.valueOf(amount))));
                value.setValueNetto(actualValueNetto
                        .add((order.getProduct().getValueNetto())
                        .multiply(BigDecimal.valueOf(amount))));
            } else {
                throw new NotFoundException("The basket is propably empty");
            }
        }

        return value;
    }

    private HttpStatus completionOfTransaction(int userID, List<Order> ordersOnBasket, BigDecimal accountBalance) {
        Value priceOfOneTransaction = getFinalPriceOfOneTransaction(ordersOnBasket);
        BigDecimal valueBruttoOfTransaction = priceOfOneTransaction.getValueBrutto();
        Transaction transaction = new Transaction(userService.findUserByID(userID),
                valueBruttoOfTransaction,
                priceOfOneTransaction.getValueNetto(),
                LocalDate.now());
        for (Order order : ordersOnBasket) {
            if (order != null) {
                Product product = order.getProduct();
                TransactionDetail transactionDetail = new TransactionDetail(product, order.getAmount(), product.getValueNetto(), product.getValueBrutto());
                getMoneyFromAccount(userID, accountBalance.subtract(valueBruttoOfTransaction));
                transaction.addDetails(transactionDetail);
                transactionService.addTransaction(transaction);
                addTransactionDetail(transactionDetail);
            } else {
                throw new NotFoundException("Basket is empty");
            }
        }
        ordersOnBasket.clear();

        return HttpStatus.ACCEPTED;
    }

    public HttpStatus addTransactionWithDetails(int userID, List<Order> ordersOnBasket) {
        Value priceOfOneTransaction = getFinalPriceOfOneTransaction(ordersOnBasket);
        BigDecimal accountBalance = userService.getAccountBalance(userID);
        int checkIfTransactionIsPossible = accountBalance.compareTo(priceOfOneTransaction.getValueBrutto());
        if (checkIfTransactionIsPossible == 1 || checkIfTransactionIsPossible == 0) {
            completionOfTransaction(userID, ordersOnBasket, accountBalance);

            return HttpStatus.ACCEPTED;
        } else {

            throw new MoneyException("Not enough money in the account.");
        }
    }

    private void getMoneyFromAccount(int userID, BigDecimal newAccountBalance) {
        userService.upgradeAccountBalance(userID, newAccountBalance);
    }
}
