package com.practice.microservices.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.microservices.dto.CartRequestBean;
import com.practice.microservices.model.Order;
import com.practice.microservices.model.OrderProduct;
import com.practice.microservices.model.Product;
import com.practice.microservices.proxy.CartServiceProxy;
import com.practice.microservices.repository.OrderRepository;
import com.practice.microservices.repository.ProductRepository;
import com.practice.microservices.util.ObjectMapperUtils;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired 
	CartServiceProxy cartServiceProxy;
	
	@Autowired 
	OrderRepository orderRepository;
	
	@Autowired 
	ProductRepository productRepository;
	
//	ModelMapper mapper;
	@Override
	public Order placeOrder(long userId) {
		// TODO Auto-generated method stub
		Order order;
		try {
			CartRequestBean cartRequestBean=cartServiceProxy.placeOrder(userId);
			
			
			
			order = ObjectMapperUtils.map(cartRequestBean, Order.class);
			Collection<OrderProduct> orderProductList= ObjectMapperUtils.mapAll(cartRequestBean.getItems(), OrderProduct.class);
			order.setOrderProducts(orderProductList);
			order.setDateCreated(LocalDate.now());
			order.setStatus("PAID");
			order.setOrderTotalAmount(cartRequestBean.getTotalAmount());
//		order.getOrderProducts().forEach(item->System.out.println(item.getProduct().getId()));
			List<Product> productList=order.getOrderProducts().stream().map(item->item.getProduct()).collect(Collectors.toList());
			productList.stream().forEach(item->productRepository.save(item));
			order=orderRepository.save(order);
			if(order.getOrderTotalAmount().intValue()>0)
				cartServiceProxy.clearItems(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			// TODO Auto-generated catch block
			
		}
			
		return order;
	}

}
