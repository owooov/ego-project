package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.manage.service.ContentManageService;
import com.ego.pojo.TbContent;
import com.ego.redis.dao.JedisDao;
import com.ego.service.ContentService;
import com.ego.utils.IDUtils;
import com.ego.utils.JsonUtil;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/24 10:21
 * @Description:
 */
@Service
public class ContentManageServiceImpl implements ContentManageService {
    @Reference
    private ContentService contentService;

    @Autowired
    private JedisDao jedisDao;

    @Value("${redis.bigAdd.key}")
    private String key;

    //根据内容分类id查询对应的所有内容并分页
    @Override
    public Map<String, Object> showAllContentByCatId(Long catId, int page, int rows) {
        return contentService.selectAllContentByCatId(catId, page, rows);
    }


    //修改
    @Override
    public EgoResult updateContent(TbContent tbContent) {
        EgoResult er= new EgoResult();
        Date date = new Date();
        tbContent.setUpdated(date);

        //修改mysql
        int result = contentService.updateContent(tbContent);
        if(result >0){
            //判断redis中是否有对应数据
            if(jedisDao.exists(key)){
                String json = jedisDao.get(key);
                List<TbContent> contentList = JsonUtil.jsonToList(json, TbContent.class);
                int index = -1;
                for (int i = 0; i < contentList.size(); i++) {
                    if((long)contentList.get(i).getId() == (long)tbContent.getId()){
                        index = i;
                    }
                }

                if(index == -1){
                    //redis与mysql数据不一致，需要清空缓存
                    jedisDao.del(key);
                }else{
                    tbContent.setCreated(contentList.get(index).getCreated());
                }
                contentList.remove(index);
                contentList.add(index,tbContent);

                //将修改后的集合再放入redis中
                String redisResult = jedisDao.insert(key, JsonUtil.objectToJson(contentList));
                if("OK".equalsIgnoreCase(redisResult)){
                    er.setStatus(200);
                }

            }
            er.setStatus(200);
        }


        return er;
    }


    //新增
    @Override
    public EgoResult insertContent(TbContent tbContent) {
        EgoResult er = new EgoResult();
        //向mysql中新增一条记录
        Date date = new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        tbContent.setId(IDUtils.genItemId());
        int result = contentService.insertContent(tbContent);

        if(result > 0){
            //mysql操作成功后，需要修改redis中数据
            if(jedisDao.exists(key)){
                String json = jedisDao.get(key);
                if(json != null && !"".equals(json)){
                    List<TbContent> contentList = JsonUtil.jsonToList(json, TbContent.class);
                    contentList.add(tbContent);
                    String redisResult = jedisDao.insert(key, JsonUtil.objectToJson(contentList));

                    if("OK".equalsIgnoreCase(redisResult)){
                        er.setStatus(200);
                    }
                }
            }
            er.setStatus(200);
        }


        return er;
    }


    //删除
    @Override
    public EgoResult deleteContent(List<Long> ids) {
        EgoResult er = new EgoResult();
        //删除mysql中对应的记录
        int index = 0;
        for (Long id : ids) {

            index += contentService.deleteContent(id);
        }
        if(index == ids.size()){
            //mysql操作成功后修改redis中数据
            if(jedisDao.exists(key)){
                String json = jedisDao.get(key);
                if(json != null && !"".equals(json)){
                    List<TbContent> contentList = JsonUtil.jsonToList(json, TbContent.class);
                    List<TbContent> removeList = new ArrayList<>();
                    for (Long id : ids) {
                        for (int i = 0; i < contentList.size(); i++) {
                            if((long)id == (long)contentList.get(i).getId()){
                                removeList.add(contentList.get(i));
                                break;
                            }
                        }
                    }

                    contentList.removeAll(removeList);
                    String redisResult = jedisDao.insert(key, JsonUtil.objectToJson(contentList));
                    if("OK".equalsIgnoreCase(redisResult)){
                        er.setStatus(200);
                    }
                }
            }
            er.setStatus(200);
        }

        return er;
    }


}
