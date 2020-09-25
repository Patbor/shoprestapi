package org.patbor.shoprestapi.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    @Column(name = "account_balance")
    private BigDecimal accountBalance;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Transaction> transactions;

    private String password;


    public void addTransaction(Transaction transaction) {
        if (transactions == null)
            transactions = new ArrayList<>();

        if (transaction != null)
            transactions.add(transaction);

        transaction.setUser(this);
    }
}
