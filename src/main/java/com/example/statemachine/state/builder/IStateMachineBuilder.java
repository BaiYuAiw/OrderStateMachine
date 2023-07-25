package com.example.statemachine.state.builder;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;

/**
 * @author libo04
 * @date 2023/7/25 15:18:16
 */
public interface IStateMachineBuilder<S, E> {
    String getName();

    StateMachine<S, E> build(BeanFactory beanFactory) throws Exception;

    //订单状态机构造器
    String ORDER_BUILDER_NAME = "orderStateMachineBuilder";
}
