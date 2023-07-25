package com.example.statemachine;

import com.example.statemachine.state.enums.OrderChangeEventEnum;
import com.example.statemachine.state.enums.OrderStatusEnum;
import com.example.statemachine.state.manager.OrderStateMachineEventManager;
import com.example.statemachine.state.model.StatusModelDO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StateMachineApplicationTests {

    @Autowired
    private OrderStateMachineEventManager manager;

    @Test
    void contextLoads() {
    }

    @Test
    void test01() {
        StatusModelDO statusModelDO = new StatusModelDO();
        statusModelDO.setOrderNumber("CCCC");
        manager.sendStatusChangeEvent(statusModelDO, OrderChangeEventEnum.CREATE_ORDER);
        manager.sendStatusChangeEvent(statusModelDO, OrderChangeEventEnum.PAY_ORDER);
        manager.sendStatusChangeEvent(statusModelDO, OrderChangeEventEnum.SEND_GOODS);

    }

    @Test
    void test02(){
        OrderStatusEnum value = OrderStatusEnum.valueOf("CREATE");
        System.out.println(value.getName());
    }

}