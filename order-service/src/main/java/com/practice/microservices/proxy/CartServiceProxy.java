package com.practice.microservices.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.practice.microservices.dto.CartRequestBean;



@FeignClient(name = "netflix-zuul-api-gatway-server")

public interface CartServiceProxy {
	@PostMapping("/cart-service/placeOrder/{userId}")
	public CartRequestBean placeOrder(@PathVariable long userId);
	
	@DeleteMapping("/cart-service/clearItems/{userId}")
	public void clearItems(@PathVariable long userId);

}
