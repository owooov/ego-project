package com.ego.search.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.pojo.TbItem;
import com.ego.search.dao.SolrDao;
import com.ego.search.service.SearchService;
import com.ego.search.vo.SolrItem;
import com.ego.service.ItemCatService;
import com.ego.service.ItemDescService;
import com.ego.service.ItemService;
import com.ego.vo.EgoResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/27 10:39
 * @Description:
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Reference
    private ItemService itemService;
    @Reference
    private ItemCatService itemCatService;
    @Reference
    private ItemDescService itemDescService;

    @Value("${solr.pageSize}")
    private int pageSize;

    @Autowired
    private CloudSolrServer cloudSolrServer;

    @Autowired
    private SolrDao solrDao;

    //初始化索引库
    @Override
    public void initSolr() {

            try {
                List<TbItem> tbItems = itemService.selectItems();
                for (TbItem tbItem : tbItems) {

                    //创建Doc对象
                    SolrInputDocument document = new SolrInputDocument();
                    document.setField("id", tbItem.getId());
                    document.setField("item_title", tbItem.getTitle());
                    document.setField("item_sell_point", tbItem.getSellPoint());
                    document.setField("item_image", tbItem.getImage());
                    document.setField("item_price", tbItem.getPrice());

                    document.setField("item_category_name", itemCatService.selectById(tbItem.getCid()).getName());
                    document.setField("item_desc", itemDescService.selectDescByItemId(tbItem.getId()).getItemDesc());
                    cloudSolrServer.add(document);
                }

                cloudSolrServer.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    //搜索商品
    @Override
    public Map<String, Object> showSolrItem(String q, int page) throws SolrServerException {
        Map<String, Object> map = new HashMap<>();
        List<SolrItem> solrItems = new ArrayList<>();
        //创建查询对象
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件
        solrQuery.setQuery("item_keywords:"+q);
        //设置分页
        solrQuery.setStart(pageSize*(page-1));
        solrQuery.setRows(pageSize);

        //开启高亮
        solrQuery.setHighlight(true);
        //设置高亮字段
        solrQuery.addHighlightField("item_title item_sell_point");
        //设置高亮前后缀
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost("</span>");

        //执行查询
        QueryResponse queryResponse = cloudSolrServer.query(solrQuery);

        SolrDocumentList responseResults = queryResponse.getResults();
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

        for (SolrDocument responseResult : responseResults) {
            SolrItem solrItem = new SolrItem();
            solrItem.setId(Long.valueOf(responseResult.getFieldValue("id").toString()));
            String item_image = responseResult.getFieldValue("item_image").toString();
            if(item_image != null){
                solrItem.setImages(item_image.split(","));
            }else{
                solrItem.setImages(new String[1]);
            }

            solrItem.setPrice(Long.valueOf(responseResult.getFieldValue("item_price").toString()));


            Map<String, List<String>> mapResult = highlighting.get("id");
            if(mapResult != null){
                List<String> titleList = mapResult.get("item_title");
                if(titleList != null && titleList.size()>0){
                    solrItem.setTitle(titleList.get(0));
                }else{
                    solrItem.setTitle(responseResult.getFieldValue("item_title").toString());
                }
                List<String> sellPointList = mapResult.get("item_sell_point");
                if(sellPointList != null && sellPointList.size()>0){
                    solrItem.setSellPoint(sellPointList.get(0));
                }else{
                    solrItem.setSellPoint(responseResult.getFieldValue("item_sell_point").toString());

                }
            }else{
                solrItem.setTitle(responseResult.getFieldValue("item_title").toString());
                solrItem.setSellPoint(responseResult.getFieldValue("item_sell_point").toString());

            }
            solrItems.add(solrItem);
        }



        long totalCounts = responseResults.getNumFound();
        map.put("itemList",solrItems);
        map.put("totalPages",(totalCounts%pageSize == 0)?totalCounts/pageSize:(totalCounts/pageSize+1));
        return map;
    }


    //根据id删除solr中数据
    @Override
    public EgoResult deleteSolrById(Map<String, String> map) {
        EgoResult er = new EgoResult();
        int result = solrDao.deleteById(map.get("id"));
        if(result == 0){
            er.setStatus(200);
        }
        return er;
    }


    //删除solr中所有数据
    @Override
    public EgoResult deleteSolrAll() {
        EgoResult er = new EgoResult();
        int result = solrDao.deleteAll();
        if(result == 0){
            er.setStatus(200);
        }
        return er;
    }


    //向solr中新增数据
    @Override
    public EgoResult insertSolr(Map<String, String> map) {
        EgoResult er = new EgoResult();
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",map.get("id"));
        document.setField("item_title", map.get("item_title"));
        document.setField("item_sell_point", map.get("item_sell_point"));
        document.setField("item_price", map.get("item_price"));
        document.setField("item_image", map.get("item_image"));
        document.setField("item_category_name", map.get("item_category_name"));
        document.setField("item_desc", map.get("item_desc"));
        int result = solrDao.add(document);
        if(result == 0){
            er.setStatus(200);
        }
        return er;
    }
}
