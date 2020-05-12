package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.manage.mq.producer.QueueProducer;
import com.ego.manage.service.ItemManageService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;
import com.ego.service.ItemCatService;
import com.ego.service.ItemDescService;
import com.ego.service.ItemParamItemService;
import com.ego.service.ItemService;
import com.ego.utils.FtpUtil;
import com.ego.utils.HttpClientUtil;
import com.ego.utils.IDUtils;
import com.ego.utils.JsonUtil;
import com.ego.vo.EgoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/17 16:19
 * @Description:
 */
@Service
public class ItemManageServiceImpl implements ItemManageService{
    @Reference
    private ItemService itemService;
    @Reference
    private ItemDescService itemDescService;
    @Reference
    private ItemCatService itemCatService;
    @Reference
    private ItemParamItemService itemParamItemService;

    @Value("${vsftpd.host}")
    private String host;
    @Value("${vsftpd.port}")
    private int port;
    @Value("${vsftpd.username}")
    private String username;
    @Value("${vsftpd.password}")
    private String password;
    @Value("${vsftpd.basePath}")
    private String basePath;
    @Value("${vsftpd.filePath}")
    private String filePath;
    @Value("${nginx.url}")
    private String nginxUrl;

    @Value("${search.url}")
    private String searchUrl;

    @Value("${redis.item.key}")
    private String key;

    @Autowired
    private JedisDao jedisDao;

    @Autowired
    private QueueProducer queueProducer;



    //查询所有商品并分页显示
    @Override
    public Map<String, Object> showAllItems(int page, int rows) {

        return itemService.selectAllItems(page, rows);
    }


    //修改商品状态
    @Override
    public EgoResult updateItemStatus(List<Long> ids, byte status) {
        EgoResult er = new EgoResult();
        int result = 0;
        for (Long id : ids) {
            result += itemService.updateItemStatus(id,status);
        }
        if(result == ids.size()){
            new Thread(){
                @Override
                public void run() {
                    for (Long id : ids) {
                        //下架、删除
                        if(status == 2 || status ==3){
                            queueProducer.sendTextMassage(id.toString());
                            //solr索引库同步
                    /*int solrResult = solrDao.deleteById(id.toString());
                    if(solrResult != 0){
                        //此时solr索引库中数据与mysql中数据不一致,清空solr
                        solrDao.deleteAll();
                    }*/
                            /*Map<String, String> map = new HashMap<>();
                            map.put("id",id.toString());
                            String json = HttpClientUtil.doPost(searchUrl + "/deleteSolrById", map);
                            EgoResult egoResult = JsonUtil.jsonToPojo(json, EgoResult.class);
                            if(egoResult.getStatus() != 200){
                                HttpClientUtil.doPost(searchUrl+"/deleteAll");
                            }

                            //redis缓存数据同步
                            jedisDao.del(key+id);*/

                            //上架
                        }else if(status == 1){
                    /*SolrInputDocument document = new SolrInputDocument();
                    document.setField("id",id);
                    TbItem tbItem = itemService.selectById(id);
                    document.setField("item_title", tbItem.getTitle());
                    document.setField("item_sell_point", tbItem.getSellPoint());
                    document.setField("item_image", tbItem.getImage());
                    document.setField("item_price", tbItem.getPrice());

                    document.setField("item_category_name", itemCatService.selectById(tbItem.getCid()).getName());
                    document.setField("item_desc", itemDescService.selectDescByItemId(tbItem.getId()).getItemDesc());
                    solrDao.add(document);*/
                            Map<String, String> map = new HashMap<>();
                            TbItem tbItem = itemService.selectById(id);
                            map.put("id",id.toString());
                            map.put("item_title",tbItem.getTitle());
                            map.put("item_sell_point", tbItem.getSellPoint());
                            map.put("item_image", tbItem.getImage());
                            map.put("item_price", tbItem.getPrice().toString());
                            map.put("item_category_name", itemCatService.selectById(tbItem.getCid()).getName());
                            map.put("item_desc", itemDescService.selectDescByItemId(tbItem.getId()).getItemDesc());

                            String json = HttpClientUtil.doPost(searchUrl+"/insertSolr",map);
                            EgoResult egoResult = JsonUtil.jsonToPojo(json, EgoResult.class);
                            if(egoResult.getStatus() != 200){
                                HttpClientUtil.doPost(searchUrl+"/deleteAll");
                            }

                            //同步redis缓存数据
                            jedisDao.insert(key+id,JsonUtil.objectToJson(tbItem));
                        }
                    }
                }
            }.start();

            er.setStatus(200);
        }
        return er;
    }


    //文件上传
    @Override
    public Map<String, Object> fileUpload(MultipartFile uploadFile) {
        Map<String, Object> map = new HashMap<>();
        try {
            String fileName = IDUtils.genImageName()+uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
            boolean result = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, fileName, uploadFile.getInputStream());
            if(result){
                map.put("error",0);
                map.put("url",nginxUrl+fileName);
            }else{
                map.put("error",1);
                map.put("message","文件上传失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }


    //新增商品(新增商品、描述表、商品规格参数商品)
    @Override
    public EgoResult insertItem(TbItem tbItem, String desc,String itemParams) {
        EgoResult er = new EgoResult();
        int result = 0;
        //新增商品
        long itemId = IDUtils.genItemId();
        Date date = new Date();
        tbItem.setId(itemId);
        tbItem.setStatus((byte)1);
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        result += itemService.insertItem(tbItem);

        //新增商品描述
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        result += itemDescService.insertItemDesc(tbItemDesc);

        //新增商品规格参数商品
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(itemId);
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        result += itemParamItemService.insertItemParamItem(tbItemParamItem);

        if(result == 3){
            er.setStatus(200);
        }
        return er;
    }

}
