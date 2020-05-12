package com.ego.manage.service;

import com.ego.pojo.TbItem;
import com.ego.vo.EgoResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/17 16:14
 * @Description:
 */
public interface ItemManageService {
    //查询所有商品并分页显示
    Map<String,Object> showAllItems(int page,int rows);

    //修改商品状态
    EgoResult updateItemStatus(List<Long> ids,byte status);

    //文件上传
    Map<String,Object> fileUpload(MultipartFile uploadFile);

    //新增商品(新增商品、描述表、商品规格参数商品)
    EgoResult insertItem(TbItem tbItem,String desc,String itemParams);


}
