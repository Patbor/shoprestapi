package org.patbor.shoprestapi.Service;


import org.patbor.shoprestapi.Entity.Product;
import org.patbor.shoprestapi.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        if (productRepository.findProductByName(product.getName()) != null)
            throw new RuntimeException("Product has already exists");
        else
            productRepository.save(product);
    }

    public List<Product> showProducts() {

        return productRepository.findAll();
    }

    public Product findProductByName(String name) {
        Product product = productRepository.findProductByName(convertFirstLetterToCapitolLetter(name));
        if (product == null)
            throw new RuntimeException("A product with that name doesn't exist");
        else

            return product;
    }

    private String convertFirstLetterToCapitolLetter(String name) {
        String firstLetter = name.substring(0,1).toUpperCase();
        String correctlyName = name.replace(name.charAt(0), firstLetter.toCharArray()[0]);

        return correctlyName;

    }

}
