package org.patbor.shoprestapi.Controller;

import org.patbor.shoprestapi.Entity.Product;
import org.patbor.shoprestapi.Entity.Transaction;
import org.patbor.shoprestapi.Entity.TransactionDetail;
import org.patbor.shoprestapi.Exceptions.MoneyException;
import org.patbor.shoprestapi.Exceptions.NotFoundException;
import org.patbor.shoprestapi.POJO.Order;
import org.patbor.shoprestapi.POJO.Value;
import org.patbor.shoprestapi.Service.ProductService;
import org.patbor.shoprestapi.Service.TransactionDetailsService;
import org.patbor.shoprestapi.Service.TransactionService;
import org.patbor.shoprestapi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private TransactionService transactionService;
    private TransactionDetailsService transactionDetailsService;
    private ProductService productService;
    private UserService userService;
    List<Order> ordersOnBasket = new ArrayList<>();

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
        ordersOnBasket.add(new Order(productService.findProductById(productId).getBody(), amount));
    }

    @GetMapping("/shopping/basket")
    public List<Order> showYourBasket() {
        return ordersOnBasket;
    }

    @GetMapping("/shopping/price")
    public String priceOfTransaction() {
        String bruttoPrice = transactionDetailsService.getFinalPriceOfOneTransaction(ordersOnBasket).getValueBrutto().toString();
        return "You have to pay: " + bruttoPrice +
                " \n If you want to finalize your transaction introduce endpoint /finalize ";
    }

    @PostMapping("/shopping/finalize/{userID}")
    public HttpStatus finalizeTransaction(@PathVariable int userID) {
      return transactionDetailsService.addTransactionWithDetails(userID, ordersOnBasket);
    }

//    @PostMapping("/find/{from},{to}")
//    public List<Transaction> findTransaction(@PathVariable("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate from,
//                                             @PathVariable("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate to) {
//
//        return transactionService.findAllTransactionByDate(from, to);
//    }
//
//    @GetMapping("/find/{from}")
//    public void findTransaction(@PathVariable("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from) {
//        // return transactionService.findAllTransactionByDate(from);
//
//    }
}
