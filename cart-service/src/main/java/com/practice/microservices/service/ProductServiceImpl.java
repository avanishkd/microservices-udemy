package com.practice.microservices.service;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.microservices.exception.ResourceNotFoundException;
import com.practice.microservices.model.Product;
import com.practice.microservices.proxy.ProductServiceProxy;
import com.practice.microservices.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private ProductServiceProxy productServiceProxy;
	private ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository,ProductServiceProxy productServiceProxy) {
		this.productRepository = productRepository;
		this.productServiceProxy=productServiceProxy;
	}

	@Override
	public Iterable<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product getProduct(@Min(value = 1, message = "Invalid Product ID.") long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	}
	
	@Override
	public List<Product> getAllProductsByIds(List<Long> productIdList) {
		long[] idArray=productIdList.stream().mapToLong(i->i).toArray();;
		
		Collection<Product> listOfProducts = productServiceProxy.getProductsByIds(idArray);
		return (List<Product>) productRepository.saveAll(listOfProducts);
	}

	
	
}