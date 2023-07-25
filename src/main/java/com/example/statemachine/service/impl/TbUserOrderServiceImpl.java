package com.example.statemachine.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.statemachine.dao.domain.TbUserOrder;
import com.example.statemachine.dao.mapper.TbUserOrderMapper;
import com.example.statemachine.service.TbUserOrderService;
import org.springframework.stereotype.Service;

/**
* @author libo04
* @description 针对表【tb_user_order】的数据库操作Service实现
* @createDate 2023-07-25 11:22:16
*/
@Service
public class TbUserOrderServiceImpl extends ServiceImpl<TbUserOrderMapper, TbUserOrder>
implements TbUserOrderService {

}
