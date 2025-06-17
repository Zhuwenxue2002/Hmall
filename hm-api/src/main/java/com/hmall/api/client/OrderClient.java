package com.hmall.api.client;

import com.hmall.api.dto.OrderApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "order-service")
public interface OrderClient {
    @PutMapping("/orders")
    public void updateOrder(@RequestBody OrderApi order);
}
