package com.example.statemachine.state.config;

import com.example.statemachine.state.enums.OrderChangeEventEnum;
import com.example.statemachine.state.enums.OrderStatusEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.guard.Guard;

/**
 * @author sugar-pocket (2565477149@qq.com)
 * @date 2023/7/25 21:01
 */
@Configuration
public class OrderStateGuardConfig {
    @Bean(name = "orderCreateGuard")
    public Guard<OrderStatusEnum, OrderChangeEventEnum> orderCreateGuard() {
        return context -> true;
    }
}
