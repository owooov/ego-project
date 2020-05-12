package com.ego.redis.dao;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/24 11:21
 * @Description:
 */
public interface JedisDao {


    //判断redis中是否有对应数据
    Boolean exists(String key);
    //新增
    String insert(String key,String value);
    //根据id删除对应数据
    Long del(String key);
    //查询
    String get(String key);

}
