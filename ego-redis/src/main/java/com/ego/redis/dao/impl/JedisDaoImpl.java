package com.ego.redis.dao.impl;

import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisCluster;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/24 11:24
 * @Description:
 */
@Repository
public class JedisDaoImpl implements JedisDao {
    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public Boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    //新增
    @Override
    public String insert(String key, String value) {
        return jedisCluster.set(key, value);
    }

    //删除
    @Override
    public Long del(String key) {
        return jedisCluster.del(key);
    }


    //查询
    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }
}
