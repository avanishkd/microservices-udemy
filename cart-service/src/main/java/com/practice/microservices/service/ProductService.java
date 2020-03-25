package com.practice.microservices.service;

import org.springframework.validation.annotation.Validated;

import com.practice.microservices.model.Product;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated
public interface ProductService {

    @NotNull Iterable<Product> getAllProducts();

    public Product getProduct(@Min(value = 1, message = "Invalid Product ID.") long id);

    Product save(Product product);

	List<Product> getAllProductsByIds(List<Long> productIdList);
}
