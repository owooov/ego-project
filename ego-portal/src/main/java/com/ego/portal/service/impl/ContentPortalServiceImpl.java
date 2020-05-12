package com.ego.portal.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.pojo.TbContent;
import com.ego.portal.service.ContentPortalService;
import com.ego.redis.dao.JedisDao;
import com.ego.service.ContentService;
import com.ego.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/24 15:54
 * @Description:
 */
@Service
public class ContentPortalServiceImpl implements ContentPortalService {
    @Reference
    private ContentService contentService;

    @Value("${redis.bigAdd.key}")
    private String key;

    @Value("${bigAdd.catId}")
    private long catId;

    @Autowired
    private JedisDao jedisDao;

    //根据内容分类id查询对应的所有内容
    @Override
    public String showBigAddContent() {

        String json = jedisDao.get(key);

        //redis中没有对应数据，从mysql中查询，并将查到的数据放到redis中
        if (!jedisDao.exists(key) || json == null || "".equals(json)) {
            List<TbContent> tbContentList = contentService.selectContentByCatId(catId);
            jedisDao.insert(key, JsonUtil.objectToJson(tbContentList));
            json = JsonUtil.objectToJson(tbContentList);
        }
        //如果redis中有对应数据，从redis中获取
        List<TbContent> contentList = JsonUtil.jsonToList(json, TbContent.class);
        List<Map<String,Object>> list = new ArrayList<>();
        for (TbContent tbContent : contentList) {
            Map<String,Object> map = new HashMap<>();
            map.put("srcB",tbContent.getPic2());
            map.put("height",240);
            map.put("alt","正在加载");
            map.put("width",670);
            map.put("src",tbContent.getPic());
            map.put("widthB",550);
            map.put("href",tbContent.getUrl());
            map.put("heightB",240);
            list.add(map);
        }

        return JsonUtil.objectToJson(list);

    }
}
