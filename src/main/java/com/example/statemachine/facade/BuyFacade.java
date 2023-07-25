package com.example.statemachine.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.statemachine.dao.domain.TbUserOrder;
import com.example.statemachine.dao.mapper.TbUserOrderMapper;
import com.example.statemachine.state.model.StatusModelDO;
import com.example.statemachine.state.enums.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author sugar-pocket (2565477149@qq.com)
 * @date 2023/7/25 21:38
 */
@Component
public class BuyFacade {


    @Autowired
    private TbUserOrderMapper orderMapper;

    /**
     * 订单创建
     */
    @Transactional
    public void orderCreate(StatusModelDO statusModelDO) {
        //自己的一些业务逻辑

        //没问题就保存订单,我这里只保存了两个重要属性 订单编号与订单状态
        TbUserOrder order = new TbUserOrder();
        order.setOrderNumber(statusModelDO.getOrderNumber());
        order.setOrderState(OrderStatusEnum.WAIT_PAYMENT.name());
        orderMapper.insert(order);
    }

    /**
     * 订单支付
     *
     * @param statusModelDO
     */
    @Transactional
    public void orderPay(StatusModelDO statusModelDO) {
        String orderNumber = statusModelDO.getOrderNumber();
        QueryWrapper<TbUserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_number",orderNumber);
        TbUserOrder order = orderMapper.selectOne(wrapper);
        if(order != null){
            //支付逻辑

            //更改状态
            order.setOrderState(OrderStatusEnum.WAIT_SEND.name());
            orderMapper.updateById(order);
        }
        //处理异常
    }


    /**
     * 订单取消
     * @param statusModelDO
     */
    @Transactional
    public void orderPayCancel(StatusModelDO statusModelDO) {
        String orderNumber = statusModelDO.getOrderNumber();
        QueryWrapper<TbUserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_number",orderNumber);
        TbUserOrder order = orderMapper.selectOne(wrapper);
        if(order != null){
            //取消逻辑

            //更改状态
            order.setOrderState(OrderStatusEnum.CANCEL.name());
            orderMapper.updateById(order);
        }
        //处理异常
    }


    /**
     * 订单发送
     * @param statusModelDO
     */
    @Transactional
    public void orderSend(StatusModelDO statusModelDO) {
        String orderNumber = statusModelDO.getOrderNumber();
        QueryWrapper<TbUserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_number",orderNumber);
        TbUserOrder order = orderMapper.selectOne(wrapper);
        if(order != null){
            //订单发送

            //更改状态
            order.setOrderState(OrderStatusEnum.WAIT_RECEIVE.name());
            orderMapper.updateById(order);
        }
        //处理异常
    }

    /**
     * 订单接收
     * @param statusModelDO
     */
    @Transactional
    public void orderReceive(StatusModelDO statusModelDO) {
        String orderNumber = statusModelDO.getOrderNumber();
        QueryWrapper<TbUserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_number",orderNumber);
        TbUserOrder order = orderMapper.selectOne(wrapper);
        if(order != null){
            //订单接收

            //更改状态
            order.setOrderState(OrderStatusEnum.COMPLETED.name());
            orderMapper.updateById(order);
        }
        //处理异常
    }

}
