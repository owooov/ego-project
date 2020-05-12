package com.ego.search.dao;

import org.apache.solr.common.SolrInputDocument;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/27 16:38
 * @Description:
 */
public interface SolrDao {
    //根据id删除solr索引库中数据
    int deleteById(String id);

    //新增
    int add(SolrInputDocument document);

    //删除所有数据
    int deleteAll();
}
