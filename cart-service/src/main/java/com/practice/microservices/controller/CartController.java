package com.practice.microservices.controller;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.practice.microservices.dto.OrderResponseDto;
import com.practice.microservices.dto.UserServiceBean;
import com.practice.microservices.model.Cart;
import com.practice.microservices.model.CartItem;
import com.practice.microservices.model.Product;
import com.practice.microservices.proxy.ProductServiceProxy;
import com.practice.microservices.repository.CartRepository;
import com.practice.microservices.repository.ProductRepository;
import com.practice.microservices.service.CartService;

@RestController
public class CartController {

	/*
	 * @Autowired UserServiceProxy productServiceProxy;
	 */

	private Logger logger = LogManager.getLogger(CartController.class);
	@Autowired
	ProductServiceProxy productServiceProxy;

//	@Autowired
//	UserServiceProxy productServiceProxy;
	@Autowired
	CartService cartService;
	@Autowired
	ProductRepository productRepo;
	@Autowired
	CartRepository cartrepo;

	@PostMapping("/addCartItems")
	public Cart addCartItems(@RequestBody Cart cart) {

		return cartService.addCartItems(cart.getUserId(), cart.getItems());

	}

	@DeleteMapping("/clearItems/{userId}")
	public void clearItems(@PathVariable long userId) {

		cartService.clearItems(userId);
	}

	@PutMapping("/getOrCreateCart/{userId}")
	public Cart getOrCreateCart(@PathVariable long userId) {

		return cartService.getOrCreateCart(userId);
	}

	@GetMapping("/getItemCount/{userId}")
	public int getItemCount(@PathVariable long userId) {

		return cartService.getItemCount(userId);
	}

	@DeleteMapping("/removeItem/{userId}")
	public Cart removeItem(@PathVariable long userId, @RequestBody Product product) {

		return cartService.removeItem(userId, product);
	}

	@GetMapping("/getTotalCartAmount/{userId}")
	public BigDecimal getTotalCartAmount(@PathVariable long userId) {

		return cartService.getTotalCartAmount(userId);
	}

	@PostMapping("/addOneProduct/{userId}")
	public Cart addOneProduct(@PathVariable long userId, @RequestBody Product product) {

		return cartService.addOneProduct(userId, product);

	}

	@DeleteMapping("/removeOneProduct/{userId}")
	public Cart removeOneProduct(@PathVariable long userId, @RequestBody Product product) {

		return cartService.removeOneProduct(userId, product);

	}

	@PostMapping("/addCartItems/{userId}")
	@HystrixCommand(fallbackMethod = "fallbackAddCartItems")
	public Cart addCartItems(@PathVariable long userId, @RequestBody Cart cart) {

		return cartService.addCartItems(userId, cart.getItems());

	}

	@PostMapping("/updateItemQuantity/{userId}")
	public Cart updateItemQuantity(@PathVariable long userId, @RequestBody CartItem cartItem) {

		return cartService.updateItemQuantity(userId, cartItem.getProduct(), cartItem.getQuantity());

	}

	@GetMapping("/checkUserStatus/{userId}")
	public UserServiceBean checkUserStatus(@PathVariable long userId) {

		return productServiceProxy.userStatus(userId);
	}

	@GetMapping("/getProductList/{idArray}")
	public Collection<Product> getProductsByIds(@PathVariable long[] idArray) {
		System.out.println("cart Service:" + idArray.length);
		Collection<Product> listtemp = productServiceProxy.getProductsByIds(idArray);
		return listtemp;
	}

	/*
	 * @PostMapping("/placeOrder/{userId}") public OrderResponseDto
	 * placeOrder(@PathVariable userId) { return null;
	 * 
	 * }
	 */

	@PostMapping("/placeOrder/{userId}")
	public OrderResponseDto placeOrder(@PathVariable long userId) {

		OrderResponseDto response = cartService.placeOrder(userId);
		logger.info("order placed in cart -service with details{}", response);

		return response;

	}

}
