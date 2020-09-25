package org.patbor.shoprestapi.Controller;

import org.patbor.shoprestapi.Entity.Product;
import org.patbor.shoprestapi.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> showProducts() {
        return productService.showProducts();
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return product;
    }

    @GetMapping("/search/{name}")
    public Product showProductByName(@PathVariable String name) {
        Product product = productService.findProductByName(name);
        return product;
    }
}
