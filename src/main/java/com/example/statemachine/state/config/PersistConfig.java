package com.example.statemachine.state.config;

import com.example.statemachine.state.enums.OrderChangeEventEnum;
import com.example.statemachine.state.enums.OrderStatusEnum;
import com.example.statemachine.state.model.PersisterDO;
import com.example.statemachine.state.persist.OrderStateMachinePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

/**
 * @author sugar-pocket (2565477149@qq.com)
 * @date 2023/7/24 23:19
 */
@Component
public class PersistConfig {
    @Autowired
    private OrderStateMachinePersist orderStateMachinePersist;

    @Bean(name = "orderStateMachinePersister")
    public StateMachinePersister<OrderStatusEnum, OrderChangeEventEnum, PersisterDO> orderStateMachinePersister() {
        return new DefaultStateMachinePersister<>(orderStateMachinePersist);
    }
}
