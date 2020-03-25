package com.practice.microservices.controller;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.microservices.model.Product;
import com.practice.microservices.service.ProductService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

	private ProductService productService;

	private Logger logger = LogManager.getLogger(ProductController.class);

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping(value = { "", "/" })
	public @NotNull Iterable<Product> getProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public @NotNull Product getProductById(@PathVariable Long id) {
		return productService.getProduct(id);
	}

	
	@GetMapping("/list/{idArray}")
	public Collection<Product> getProductsByIds(@PathVariable long[] idArray) {
		Collection<Product> products = productService.getAllProductsByIds(idArray);
		if (idArray.length != products.size())
			logger.warn(
					"unable to fetch all products, some products with given id might not be avialable, products fetched->{}",
					products);

		logger.info(" all products with given id fetched successfully->{}", products);
		return products;
	}

}
