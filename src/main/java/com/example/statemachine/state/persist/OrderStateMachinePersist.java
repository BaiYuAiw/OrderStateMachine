package com.example.statemachine.state.persist;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.statemachine.dao.domain.TbUserOrder;
import com.example.statemachine.dao.mapper.TbUserOrderMapper;
import com.example.statemachine.state.enums.OrderChangeEventEnum;
import com.example.statemachine.state.enums.OrderStatusEnum;
import com.example.statemachine.state.model.PersisterDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author sugar-pocket (2565477149@qq.com)
 * @date 2023/7/24 23:20
 */

@Component
public class OrderStateMachinePersist implements StateMachinePersist<OrderStatusEnum, OrderChangeEventEnum, PersisterDO> {

    @Autowired
    private TbUserOrderMapper userOrderMapper;

    //将订单状态写入数据库
    @Override
    public void write(StateMachineContext<OrderStatusEnum, OrderChangeEventEnum> stateMachineContext, PersisterDO persisterDO) throws Exception {
        String orderNumber = persisterDO.getOrderNumber();
        if(orderNumber == null){
            throw new RuntimeException("orderNumber 为空");
        }
        QueryWrapper<TbUserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_number",orderNumber);
        TbUserOrder order = userOrderMapper.selectOne(wrapper);
        if(order != null){
            order.setOrderState(persisterDO.getCurrentState());
            order.setUpdateTime(new Date());
            userOrderMapper.updateById(order);
        }
    }

    @Override
    public StateMachineContext<OrderStatusEnum, OrderChangeEventEnum> read(PersisterDO persisterDO) throws Exception {
        //将数据从数据库中读出,然后将订单的状态设置给参数orderStatusEnum
        OrderStatusEnum orderStatusEnum;
        QueryWrapper<TbUserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_number",persisterDO.getOrderNumber());
        TbUserOrder order = userOrderMapper.selectOne(wrapper);
        if(order != null){
            orderStatusEnum = OrderStatusEnum.valueOf(order.getOrderState());
        }else{
            orderStatusEnum = OrderStatusEnum.CREATE;
        }
        return new DefaultStateMachineContext<>(orderStatusEnum, null, null, null, null, persisterDO.getMachineId());
    }
}
