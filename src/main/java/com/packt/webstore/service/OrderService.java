package com.packt.webstore.service;

/**
 * Created by dman on 7/9/16.
 */
public interface OrderService {
    void processOrder(String productId, long count);
}
