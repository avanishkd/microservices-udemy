package com.practice.microservices.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "netflix-zuul-api-gatway-server")
//@RibbonClient(name = "user-service")
public interface UserServiceProxy {
	

}
