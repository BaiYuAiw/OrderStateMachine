package com.example.statemachine.state.builder;

import com.example.statemachine.state.enums.OrderChangeEventEnum;
import com.example.statemachine.state.enums.OrderStatusEnum;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 * @author libo04
 * @date 2023/7/25 15:19:03
 */
@Component
public class OrderStateMachineBuilder implements IStateMachineBuilder<OrderStatusEnum, OrderChangeEventEnum> {
    /**
     * 注入状态分支条件
     */
    @Autowired
    private Guard<OrderStatusEnum, OrderChangeEventEnum> orderCreateGuard;

    /**
     * 注入状态转移动作
     */
    @Autowired
    private Action<OrderStatusEnum, OrderChangeEventEnum> errorHandlerAction;

    @Autowired
    private Action<OrderStatusEnum, OrderChangeEventEnum> orderCreateAction;

    @Autowired
    private Action<OrderStatusEnum, OrderChangeEventEnum> orderPayAction;

    @Autowired
    private Action<OrderStatusEnum, OrderChangeEventEnum> orderPayCancelAction;

    @Autowired
    private Action<OrderStatusEnum, OrderChangeEventEnum> orderSendAction;

    @Autowired
    private Action<OrderStatusEnum, OrderChangeEventEnum> orderReceiveAction;

    @Override
    public StateMachine<OrderStatusEnum, OrderChangeEventEnum> build(BeanFactory beanFactory) throws Exception {
        StateMachineBuilder.Builder<OrderStatusEnum, OrderChangeEventEnum> builder = StateMachineBuilder.builder();
        //设置id
        builder.configureConfiguration()
                .withConfiguration()
                .autoStartup(true)
                .beanFactory(beanFactory)
                .machineId(ORDER_BUILDER_NAME + "Id");

//初始化状态机，并指定状态集合
        builder.configureStates()
                .withStates()
                //初始状态
                .initial(OrderStatusEnum.CREATE)
                .end(OrderStatusEnum.COMPLETED)
                .states(EnumSet.allOf(OrderStatusEnum.class));

        //定义状态机节点，即迁移动作
        builder.configureTransitions()
                //订单的初始状态为虚拟状态为了满足节点跳转
                .withExternal()
                //当前节点状态-订单创建
                .source(OrderStatusEnum.CREATE)
                //目标节点状态-待支付
                .target(OrderStatusEnum.WAIT_PAYMENT)
                //导致当前变化的事件，用户下单
                .event(OrderChangeEventEnum.CREATE_ORDER)
                /**
                 * 执行相关动作的前置条件判断
                 * orderCreateGuard为Guard<S, E>接口的实现类
                 */
                .guard(orderCreateGuard)
                /**
                 * 执行当前状态变更导致的业务逻辑处理，以及异常处理
                 * orderCreateAction、errorHandlerAction为Action<S, E>接口的实现类
                 */
                .action(orderCreateAction, errorHandlerAction)

                //支付订单
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_PAYMENT)
                .target(OrderStatusEnum.WAIT_SEND)
                .event(OrderChangeEventEnum.PAY_ORDER)
                .action(orderPayAction, errorHandlerAction)

                //取消支付订单
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_PAYMENT)
                .target(OrderStatusEnum.CANCEL)
                .event(OrderChangeEventEnum.PAY_CANCEL)
                .action(orderPayCancelAction, errorHandlerAction)

                //发货
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_SEND)
                .target(OrderStatusEnum.WAIT_RECEIVE)
                .event(OrderChangeEventEnum.SEND_GOODS)
                .action(orderSendAction, errorHandlerAction)

                //收货
                .and()
                .withExternal()
                .source(OrderStatusEnum.WAIT_RECEIVE)
                .target(OrderStatusEnum.COMPLETED)
                .event(OrderChangeEventEnum.RECEIVE_GOODS)
                .action(orderReceiveAction, errorHandlerAction)

        ;

        return builder.build();
    }

    @Override
    public String getName() {
        return ORDER_BUILDER_NAME;
    }
}
