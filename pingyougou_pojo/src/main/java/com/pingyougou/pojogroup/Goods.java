package com.pingyougou.pojogroup;

import com.pingyougou.pojo.TbGoods;
import com.pingyougou.pojo.TbGoodsDesc;
import com.pingyougou.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

/**
 * @author yin
 * @Date 2018/8/16 15:50
 * @Method
 */
public class Goods implements Serializable {
    private TbGoods goods;
    private TbGoodsDesc goodsDesc;
    private List<TbItem> itemList;

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<TbItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TbItem> itemList) {
        this.itemList = itemList;
    }
}
