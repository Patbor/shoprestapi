package org.patbor.shoprestapi.Service;


import org.patbor.shoprestapi.Entity.Product;
import org.patbor.shoprestapi.Exceptions.AlreadyExistsException;
import org.patbor.shoprestapi.Exceptions.NotFoundException;
import org.patbor.shoprestapi.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<Product> addProduct(Product product) {
        if (productRepository.findProductByName(product.getName()) != null) {
            throw new AlreadyExistsException("Product has already existed");
        }
        else {

            return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
        }
    }

    public ResponseEntity<List<Product>> showProducts() {

        return new ResponseEntity<>(productRepository.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<Product> findProductByName(String name) {
        Product product = productRepository.findProductByName(convertFirstLetterToCapitolLetter(name));
        if (product == null) {
            throw new NotFoundException("A product with name " + name + " doesn't exist");
        } else {

            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }

    public ResponseEntity<Product> findProductById(int id) {
        Optional<Product> OptProduct = productRepository.findById(id);
        Product product = null;
        if (OptProduct.isPresent()) {
            product = OptProduct.get();
        } else {
            throw new NotFoundException("There is no product with id: " + id);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    private String convertFirstLetterToCapitolLetter(String name) {
        String firstLetter = name.substring(0, 1).toUpperCase();
        String correctlyName = name.replace(name.charAt(0), firstLetter.toCharArray()[0]);

        return correctlyName;
    }
    public int getAmountOfProduct(String name) {
       return productRepository.getAmount(name);
    }

    public void uptadeAmountOfProducts(String name, int amount) {
        productRepository.updateAmount(amount, name);
    }
}
