package com.ego.item.vo;

import java.util.List;

/**
 * @Author: Victor Wu
 * @Date: 2019/9/29 09:29
 * @Description:
 */
public class ItemParamItem {
    private String group;
    private List<ItemParamItemVo> params;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<ItemParamItemVo> getParams() {
        return params;
    }

    public void setParams(List<ItemParamItemVo> params) {
        this.params = params;
    }
}
