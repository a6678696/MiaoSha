package com.ledao.constant;

/**
 * @author LeDao
 * @company
 * @create 2022-03-30 1:28
 */
public class Constant {

    /**
     * redis token前缀
     */
    public static final String REDIS_TOKEN_PREFIX = "tk";

    /**
     * redis 秒杀库存前缀
     */
    public static final String REDIS_STOCK_PREFIX = "st";

    /**
     * redis 秒杀商品是否秒杀完标识前缀
     */
    public static final String REDIS_GOODS_MIAOSHA_OVER_PREFIX = "over";

    /**
     * redis token有效期30分钟
     */
    public static final Integer REDIS_TOKEN_EXPIRE = 30 * 60;

    /**
     * redis 秒杀商品集合
     */
    public static final String REDIS_MIAOSHA_GOODS = "miaosha_goods_list";

    /**
     * redis 秒杀商品集合有效期60分钟
     */
    public static final Integer REDIS_MIAOSHA_GOODS_EXPIRE = 60 * 60;

    /**
     * redis 验证码前缀
     */
    public static final String REDIS_VERIFYCODE_PREFIX = "vc";

    /**
     * redis 验证码有效期5分钟
     */
    public static final Integer REDIS_VERIFYCODE_EXPIRE = 5 * 60;
}
