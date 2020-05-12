package com.ego.manage.listener;

import com.ego.redis.dao.JedisDao;
import com.ego.utils.HttpClientUtil;
import com.ego.utils.JsonUtil;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/29 16:51
 * @Description:
 */
public class MyMessageListener implements MessageListener{

    @Value("${search.url}")
    private String searchUrl;
    @Autowired
    private JedisDao jedisDao;
    @Value("${redis.item.key}")
    private String key;
    @Override
    public void onMessage(Message message) {
        try {
            Map<String, String> map = new HashMap<>();
            TextMessage textMessage = (TextMessage) message;
            map.put("id",textMessage.getText());

            String json = HttpClientUtil.doPost(searchUrl + "/deleteSolrById", map);
            EgoResult egoResult = JsonUtil.jsonToPojo(json, EgoResult.class);
            if(egoResult.getStatus() != 200){
                HttpClientUtil.doPost(searchUrl+"/deleteAll");
            }

            //redis缓存数据同步
            jedisDao.del(key+((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
