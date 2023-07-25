package com.example.statemachine.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.statemachine.dao.domain.TbUserOrder;
import org.apache.ibatis.annotations.Mapper;

/**
* @author libo04
* @description 针对表【tb_user_order】的数据库操作Mapper
* @createDate 2023-07-25 11:22:16
* @Entity dao.domain.TbUserOrder
*/
@Mapper
public interface TbUserOrderMapper extends BaseMapper<TbUserOrder> {


}
