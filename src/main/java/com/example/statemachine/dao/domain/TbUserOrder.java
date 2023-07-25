package com.example.statemachine.dao.domain;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName tb_user_order
 */
@Data
@TableName("tb_user_order")
public class TbUserOrder implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 订单编码
     */
    private String orderNumber;

    /**
     * 支付人
     */
    private String yid;

    /**
     * 商品id
     */
    private String goodsNumber;

    /**
     * 商品描述
     */
    private String goodsName;

    /**
     * 订单价格
     */
    private Integer orderPrice;

    /**
     * 订单状态
     */
    private String orderState;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}