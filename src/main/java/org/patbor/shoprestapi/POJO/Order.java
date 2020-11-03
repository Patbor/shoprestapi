package org.patbor.shoprestapi.POJO;

import lombok.Getter;
import lombok.Setter;
import org.patbor.shoprestapi.Entity.Product;


@Getter
@Setter
public class Order {

    private Product product;
    private int amount;

    public Order(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "product=" + product +
                ", amount=" + amount +
                '}';
    }
}
