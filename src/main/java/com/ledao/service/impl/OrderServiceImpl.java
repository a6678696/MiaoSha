package com.ledao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.ledao.entity.MiaoShaGoods;
import com.ledao.entity.Order;
import com.ledao.mapper.GoodsMapper;
import com.ledao.mapper.MiaoShaGoodsMapper;
import com.ledao.mapper.OrderMapper;
import com.ledao.service.OrderService;
import com.ledao.util.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author LeDao
 * @company
 * @create 2022-04-02 22:40
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private MiaoShaGoodsMapper miaoShaGoodsMapper;

    @Override
    public List<Order> list(Map<String, Object> map) {
        return orderMapper.list(map);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int add(Order order) {
        miaoShaGoodsMapper.reduceStock(order.getMiaoShaGoodsId());
        goodsMapper.reduceStock(order.getGoodsId());
        int result = orderMapper.add(order);
        Gson gson = new Gson();
        String key = "miaoShaGoods_" + order.getMiaoShaGoodsId();
        MiaoShaGoods miaoShaGoods = gson.fromJson(RedisUtil.getKeyValue(key), MiaoShaGoods.class);
        miaoShaGoods.setStock(miaoShaGoods.getStock() - 1);
        RedisUtil.setKey(key, gson.toJson(miaoShaGoods));
        //获取秒杀时长
        long time = (miaoShaGoods.getEndTime().getTime() - System.currentTimeMillis()) / 1000;
        //设置过期时间
        RedisUtil.setKeyTime(key, time);
        return result;
    }

    @Override
    public int update(Order order) {
        return orderMapper.updateById(order);
    }

    @Override
    public Order findById(Integer id) {
        return orderMapper.selectById(id);
    }

    @Override
    public Order findByUserIdAndMiaoShaGoodsId(Map<String, Object> map) {
        return orderMapper.findByUserIdAndMiaoShaGoodsId(map);
    }
}
