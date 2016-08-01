package com.packt.webstore.service;

import com.packt.webstore.domain.Product;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Created by dman on 7/9/16.
 */
public interface ProductService {
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    Set<Product> getProductsByFilter(Map<String, List<String>> filterParams);
    Product getProductById(String productID);
//
//    List<Product> getProductsByCategory(String category);
//
//    Set<Product> getProductsByFilter(Map<String, List<String>> filterParams);
}
