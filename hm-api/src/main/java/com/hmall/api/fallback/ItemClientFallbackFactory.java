package com.hmall.api.fallback;

import com.hmall.api.client.ItemClient;
import com.hmall.api.dto.ItemApiDTO;
import com.hmall.api.dto.OrderDetailApiDTO;
import com.hmall.common.utils.CollUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collection;
import java.util.List;

@Slf4j
public class ItemClientFallbackFactory implements FallbackFactory<ItemClient> {
    @Override
    public ItemClient create(Throwable cause) {
        return new ItemClient() {
            @Override
            public List<ItemApiDTO> queryItemByIds(Collection<Long> ids) {
                log.error("查询商品失败", cause);
                return CollUtils.emptyList();
            }

            @Override
            public void deductStock(List<OrderDetailApiDTO> items) {
                log.error("减少商品库存失败", cause);
                throw new RuntimeException(cause);
            }
        };
    }
}
