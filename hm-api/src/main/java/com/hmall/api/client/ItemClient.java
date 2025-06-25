package com.hmall.api.client;

import com.hmall.api.dto.ItemApiDTO;
import com.hmall.api.dto.OrderDetailApiDTO;
import com.hmall.api.fallback.ItemClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@FeignClient(value = "item-service", fallbackFactory = ItemClientFallbackFactory.class)
public interface ItemClient {
    @GetMapping("/items")
    List<ItemApiDTO> queryItemByIds(@RequestParam("ids") Collection<Long> ids );

    @PutMapping("/items/stock/deduct")
    public void deductStock(@RequestBody List<OrderDetailApiDTO> items);
}
