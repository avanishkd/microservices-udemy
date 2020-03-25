package com.practice.microservices.proxy;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.practice.microservices.dto.UserServiceBean;
import com.practice.microservices.model.Product;

@FeignClient(name = "netflix-zuul-api-gatway-server")
//@RibbonClient(name = "product-service")
public interface ProductServiceProxy {

	@GetMapping("/product-service/api/products/list/{idArray}")
	public Collection<Product> getProductsByIds(@PathVariable long[] idArray);
	
	@GetMapping("/user-service/api/users/userStatus/{userId}")
	public UserServiceBean userStatus(@PathVariable long userId);
	
	

}
