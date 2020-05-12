package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.manage.service.ContentCatManageService;
import com.ego.pojo.TbContentCategory;
import com.ego.service.ContentCatService;
import com.ego.utils.IDUtils;
import com.ego.vo.EgoResult;
import com.ego.vo.EgoTree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/20 10:18
 * @Description:
 */
@Service
public class ContentCatManageServiceImpl implements ContentCatManageService {
    @Reference
    private ContentCatService contentCatService;

    //根据父节点id查询所有子节点
    @Override
    public List<EgoTree> showContentCatByPid(Long pid) {
        List<EgoTree> treeList = new ArrayList<>();
        List<TbContentCategory> contentCategoryList = contentCatService.selectContentCatByPid(pid);
        for (TbContentCategory tbContentCategory : contentCategoryList) {
            EgoTree tree = new EgoTree();
            tree.setId(tbContentCategory.getId());
            tree.setText(tbContentCategory.getName());
            tree.setState(tbContentCategory.getIsParent()?"closed":"open");

            treeList.add(tree);
        }
        return treeList;
    }


    //新增
    @Override
    public EgoResult insertContentCat(TbContentCategory tbContentCategory) {
        EgoResult er = new EgoResult();
        //查询当前父节点下是否有与新增节点同名的其他子节点
        List<TbContentCategory> categoryList = contentCatService.selectContentCatByCondition(tbContentCategory);
        //如果有，则不进行新增
        if(categoryList != null && categoryList.size()>0){
            er.setStatus(400);
        }else{
            //如果没有，则新增
            tbContentCategory.setId(IDUtils.genItemId());
            tbContentCategory.setIsParent(false);
            tbContentCategory.setStatus(1);
            tbContentCategory.setSortOrder(1);
            Date date = new Date();
            tbContentCategory.setCreated(date);
            tbContentCategory.setUpdated(date);
            int result = contentCatService.insertContentCat(tbContentCategory);

            if(result > 0){
                //修改父节点的状态，is-parent改为true
                TbContentCategory parentContentCat = new TbContentCategory();
                parentContentCat.setId(tbContentCategory.getParentId());
                parentContentCat.setIsParent(true);
                int index = contentCatService.updateContentCat(parentContentCat);

                if(index > 0){
                    er.setStatus(200);
                    er.setData(tbContentCategory);
                }
            }

        }
        return er;
    }


    //重命名
    @Override
    public EgoResult updateContentCat(TbContentCategory tbContentCategory) {
        EgoResult er = new EgoResult();
        //查询当前节点详情
        TbContentCategory category = new TbContentCategory();
        category.setId(tbContentCategory.getId());
        List<TbContentCategory> categoryList = contentCatService.selectContentCatByCondition(category);
        if(categoryList != null && categoryList.size()>0){
            category = categoryList.get(0);
        }

        //查询当前节点的父节点下是否有与修改后的节点名称相同的其他子节点
        TbContentCategory otherContentCat = new TbContentCategory();
        otherContentCat.setParentId(category.getParentId());
        otherContentCat.setName(tbContentCategory.getName());
        List<TbContentCategory> tbContentCategories = contentCatService.selectContentCatByCondition(otherContentCat);
        //如果有
        if(tbContentCategories != null && tbContentCategories.size()>0){
            er.setStatus(400);
        }else{
            //如果没有
            int result = contentCatService.updateContentCat(tbContentCategory);
            if(result > 0){
                er.setStatus(200);
            }
        }


        return er;
    }


    //删除
    @Override
    public EgoResult deleteContentCat(TbContentCategory tbContentCategory) {
        EgoResult er = new EgoResult();
        //修改当前节点的status为2
        tbContentCategory.setStatus(2);
        int result = contentCatService.updateContentCat(tbContentCategory);
        if(result >0){
            //查询当前节点的详情
            List<TbContentCategory> categoryList = contentCatService.selectContentCatByCondition(tbContentCategory);
            if(categoryList != null && categoryList.size()>0){
                tbContentCategory= categoryList.get(0);
            }
            //查询当前节点的父节点下是否有其他status为1的其他子节点
            TbContentCategory otherContentCat = new TbContentCategory();
            otherContentCat.setParentId(tbContentCategory.getParentId());
            otherContentCat.setStatus(1);
            List<TbContentCategory> otherContentCatList = contentCatService.selectContentCatByCondition(otherContentCat);
            //如果没有
            if(!(otherContentCatList != null && otherContentCatList.size()>0)){
                //修改当前节点的父节点的isParent为false
                TbContentCategory parentContentCat = new TbContentCategory();
                parentContentCat.setId(tbContentCategory.getParentId());
                parentContentCat.setIsParent(false);
                int index = contentCatService.updateContentCat(parentContentCat);

                if(index >0){
                    er.setStatus(200);
                }
            }else{
                er.setStatus(200);
            }

        }


        return er;
    }
}
