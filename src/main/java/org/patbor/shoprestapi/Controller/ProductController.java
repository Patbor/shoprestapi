package org.patbor.shoprestapi.Controller;

import org.patbor.shoprestapi.Entity.Product;
import org.patbor.shoprestapi.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Product>> showProducts() {
        return productService.showProducts();

    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return productService.addProduct(product);

    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Product> showProductByName(@PathVariable String name) {
        ResponseEntity<Product> product = productService.findProductByName(name);
        return product;

    }

    @GetMapping("/searchId/{id}")
    public ResponseEntity<Product> findProductByID(@PathVariable int id) {
        ResponseEntity<Product> product = productService.findProductById(id);
        return product;

    }
}
