package com.ego.manage.dao.impl;

import com.ego.manage.dao.SolrDao;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/27 16:42
 * @Description:
 */
@Repository
public class SolrDaoImpl implements SolrDao {
    @Autowired
    private CloudSolrServer cloudSolrServer;

    //根据id删除solr索引库中数据
    @Override
    public int deleteById(String id) {
        UpdateResponse updateResponse = null;
        int status = -1;
        try {
            updateResponse = cloudSolrServer.deleteById(id);
            status = updateResponse.getStatus();
            cloudSolrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


    //新增
    @Override
    public int add(SolrInputDocument document) {
        UpdateResponse updateResponse = null;
        int status = -1;
        try {
            updateResponse = cloudSolrServer.add(document);
            status = updateResponse.getStatus();
            cloudSolrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
            return status;
    }


    //删除所有数据
    @Override
    public int deleteAll() {
        UpdateResponse updateResponse = null;
        int status = -1;
        try {
            updateResponse = cloudSolrServer.deleteByQuery("*:*");
            status = updateResponse.getStatus();
            cloudSolrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
