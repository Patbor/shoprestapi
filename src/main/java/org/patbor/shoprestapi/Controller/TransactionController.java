package org.patbor.shoprestapi.Controller;

import org.patbor.shoprestapi.Entity.Product;
import org.patbor.shoprestapi.Entity.Transaction;
import org.patbor.shoprestapi.Entity.TransactionDetail;
import org.patbor.shoprestapi.POJO.Order;
import org.patbor.shoprestapi.POJO.Value;
import org.patbor.shoprestapi.Service.ProductService;
import org.patbor.shoprestapi.Service.TransactionDetailsService;
import org.patbor.shoprestapi.Service.TransactionService;
import org.patbor.shoprestapi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private TransactionService transactionService;
    private TransactionDetailsService transactionDetailsService;
    private ProductService productService;
    private UserService userService;
    List<Order> orders = new ArrayList<>();
    @Autowired
    public TransactionController(TransactionService transactionService,
                                 TransactionDetailsService transactionDetailsService,
                                 ProductService productService,
                                 UserService userService) {

        this.transactionService = transactionService;
        this.transactionDetailsService = transactionDetailsService;
        this.productService = productService;
        this.userService = userService;
    }

    @PostMapping("/shopping/{productId},{amount}")
    public void doShopping(@PathVariable int productId, @PathVariable int amount) {
        orders.add(new Order(productService.findProductById(productId), amount));
    }

    @GetMapping
    public List<Order> showYourBasket() {
        return orders;
    }

    @GetMapping("/shopping/price")
    public String priceOfTransaction() {
        return "You have to paid: " + values().getValueBrutto().toString() +
                " \n If you want to finalize your transaction introduce endpoint /finalize ";
    }

    @PostMapping("/shopping/finalize/{userID}")
    public void finalizeTransaction(@PathVariable int userID) {
        Value value = values();
        Transaction transaction = new Transaction(userService.findUserByID(userID), 
                value.getValueBrutto(),
                value.getValueNetto(), 
                LocalDate.now());
        transactionService.addTransaction(transaction);
        for (Order order : orders) {
            Product product = order.getProduct();
            TransactionDetail transactionDetail = new TransactionDetail(product, order.getAmount(), product.getValueNetto(), product.getValueBrutto());
           
            transaction.addDetails(transactionDetail);
            transactionDetailsService.addTransactionDetail(transactionDetail);
        }
    }

    private Value values() {
        Value value = new Value();

        for (Order order: orders) {
            BigDecimal actualValueBrutto = value.getValueBrutto();
            BigDecimal actualValueNetto = value.getValueNetto();
            int amount = order.getAmount();

            value.setValueBrutto(actualValueBrutto
                    .add((order.getProduct().getValueBrutto())
                    .multiply(BigDecimal.valueOf(amount))));

            value.setValueNetto(actualValueNetto
                    .add((order.getProduct().getValueNetto()))
                    .multiply(BigDecimal.valueOf(amount)));
        }

        return value;
    }
}
