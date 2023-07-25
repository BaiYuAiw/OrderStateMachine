package com.example.statemachine.state.manager;

import com.example.statemachine.state.builder.IStateMachineBuilder;
import com.example.statemachine.state.enums.OrderChangeEventEnum;
import com.example.statemachine.state.enums.OrderStatusEnum;
import com.example.statemachine.state.model.PersisterDO;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author sugar-pocket (2565477149@qq.com)
 * @date 2023/7/25 22:25
 */
@Component
public class OrderStateMachineEventManager extends AbstractStateMachineEventManager<OrderStatusEnum, OrderChangeEventEnum> {
    @Resource(name = "orderStateMachinePersister")
    private StateMachinePersister<OrderStatusEnum, OrderChangeEventEnum, PersisterDO> stateMachinePersister;

    @Override
    public void stateMachinePersist(StateMachine<OrderStatusEnum, OrderChangeEventEnum> stateMachine,
                                    PersisterDO persisterDO) throws Exception {
        stateMachinePersister.persist(stateMachine, persisterDO);
    }

    @Override
    public StateMachine<OrderStatusEnum, OrderChangeEventEnum> stateMachineRestore(
            StateMachine<OrderStatusEnum, OrderChangeEventEnum> stateMachine,
            PersisterDO persisterDO) throws Exception {

        persisterDO.setMachineId(IStateMachineBuilder.ORDER_BUILDER_NAME);
        return stateMachinePersister.restore(stateMachine, persisterDO);
    }

    @Override
    public String getStateMachineType() {
        return IStateMachineBuilder.ORDER_BUILDER_NAME;
    }
}
