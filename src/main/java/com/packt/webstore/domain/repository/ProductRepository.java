package com.packt.webstore.domain.repository;

import com.packt.webstore.domain.Product;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dman on 7/8/16.
 */
public interface ProductRepository {
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    Set<Product> getProductsByFilter(Map<String, List<String>> filterParams);
    Product getProductById(String productID);
}
