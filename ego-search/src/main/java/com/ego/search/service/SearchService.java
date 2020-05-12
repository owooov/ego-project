package com.ego.search.service;

import com.ego.vo.EgoResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/27 10:35
 * @Description:
 */
public interface SearchService {
    //初始化索引库
    void initSolr();

    //搜索商品
    Map<String,Object> showSolrItem(String q,int page) throws SolrServerException;

    //根据id删除solr中数据
    EgoResult deleteSolrById(Map<String,String> map);

    //删除solr中所有数据
    EgoResult deleteSolrAll();


    //向solr中新增数据
    EgoResult insertSolr(Map<String, String> map);
}
