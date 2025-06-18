package com.hmall.api.config;

import com.hmall.common.utils.UserContext;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor(){
            @Override
            public void apply(feign.RequestTemplate requestTemplate) {
                Long userid = UserContext.getUser();
                if (userid != null)
                {
                    requestTemplate.header("user-info", userid.toString());
                }
            }
        };
    }
}
