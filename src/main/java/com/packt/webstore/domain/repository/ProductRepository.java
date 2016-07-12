package com.packt.webstore.domain.repository;

import com.packt.webstore.domain.Product;

import java.util.List;

/**
 * Created by dman on 7/8/16.
 */
public interface ProductRepository {
    List<Product> getAllProducts();
    Product getProductById(String productID);
}
