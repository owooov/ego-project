package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.item.service.ItemCatConsumerService;
import com.ego.item.vo.CatMenu;
import com.ego.item.vo.CatMenuChild;
import com.ego.pojo.TbItemCat;
import com.ego.service.ItemCatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/19 17:22
 * @Description:
 */
@Service
public class ItemCatConsumerServiceImpl implements ItemCatConsumerService {
    @Reference
    private ItemCatService itemCatService;

    //显示所有的商品分类
    @Override
    public CatMenu showAllItemCat() {
        List<Object> list = getAllItemCat(itemCatService.selectAllItemCat(0L));
        CatMenu cm = new CatMenu();
        cm.setData(list);
        return cm;
    }

    private List<Object> getAllItemCat(List<TbItemCat> list){
        List<Object> catList = new ArrayList<>();

        for (TbItemCat tbItemCat : list) {
            //判断当前节点是否为父节点
            if(tbItemCat.getIsParent()){
                CatMenuChild cmc = new CatMenuChild();
                cmc.setU("/products/"+tbItemCat.getId()+".html");
                cmc.setN("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                cmc.setI(getAllItemCat(tbItemCat.getChildren()));

                catList.add(cmc);
            }else{
                catList.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
            }
        }

        return catList;
    }
}
