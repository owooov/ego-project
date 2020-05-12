package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.manage.service.ItemCatManageService;
import com.ego.pojo.TbItemCat;
import com.ego.service.ItemCatService;
import com.ego.vo.EgoTree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/18 11:36
 * @Description:
 */
@Service
public class ItemCatManageServiceImpl implements ItemCatManageService {
    @Reference
    private ItemCatService itemCatService;

    //根据父节点id查询所有状态正常的子节点
    @Override
    public List<EgoTree> selectItemCatsByPid(Long pid) {
        List<TbItemCat> tbItemCats = itemCatService.selectItemCatsByPid(pid);
        List<EgoTree> treeList = new ArrayList<>();
        for (TbItemCat tbItemCat : tbItemCats) {
            EgoTree tree = new EgoTree();
            tree.setId(tbItemCat.getId());
            tree.setText(tbItemCat.getName());
            tree.setState(tbItemCat.getIsParent()?"closed":"open");

            treeList.add(tree);
        }
        
        return treeList;
    }
}
