package com.example.statemachine.state.config;

import com.example.statemachine.facade.BuyFacade;
import com.example.statemachine.state.model.StatusModelDO;
import com.example.statemachine.state.model.StateMachineConstants;
import com.example.statemachine.state.enums.OrderChangeEventEnum;
import com.example.statemachine.state.enums.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;

/**
 * @author sugar-pocket (2565477149@qq.com)
 * @date 2023/7/25 21:02
 */
@Configuration
public class OrderStateActionConfig {
    @Autowired
    private BuyFacade buyFacade;

    @Bean(name = "errorHandlerAction")
    public Action<OrderStatusEnum, OrderChangeEventEnum> errorHandlerAction() {

        return context -> {
            RuntimeException exception = (RuntimeException) context.getException();
            context.getStateMachine()
                    .getExtendedState().getVariables()
                    .put(RuntimeException.class, exception);
        };
    }

    @Bean(name = "orderCreateAction")
    public Action<OrderStatusEnum, OrderChangeEventEnum> orderCreateAction() {
        return context -> {
            buyFacade.orderCreate((StatusModelDO)context.getMessage().getHeaders().get(StateMachineConstants.STATE_MODEL_DTO));
        };
    }


    @Bean(name = "orderPayAction")
    public Action<OrderStatusEnum, OrderChangeEventEnum> orderPayAction() {
        return context -> {
            buyFacade.orderPay((StatusModelDO) context.getMessage().getHeaders().get(StateMachineConstants.STATE_MODEL_DTO));
        };
    }

    @Bean(name = "orderPayCancelAction")
    public Action<OrderStatusEnum, OrderChangeEventEnum> orderPayCancelAction() {
        return context -> {
            buyFacade.orderPayCancel((StatusModelDO) context.getMessage().getHeaders().get(StateMachineConstants.STATE_MODEL_DTO));
        };
    }

    @Bean(name = "orderSendAction")
    public Action<OrderStatusEnum, OrderChangeEventEnum> orderSendAction() {
        return context -> {
            buyFacade.orderSend((StatusModelDO) context.getMessage().getHeaders().get(StateMachineConstants.STATE_MODEL_DTO));
        };
    }

    @Bean(name = "orderReceiveAction")
    public Action<OrderStatusEnum, OrderChangeEventEnum> orderReceiveAction() {
        return context -> {
            buyFacade.orderReceive((StatusModelDO) context.getMessage().getHeaders().get(StateMachineConstants.STATE_MODEL_DTO));
        };
    }
}
