package com.pingyougou.sellergoods.service;

import com.pingyougou.pojo.PageResult;
import com.pingyougou.pojo.TbBrand;

import java.util.List;

/**
 * @author yin
 * @Date 2018/8/11 22:11
 * @Method
 */

public interface BrandService {
    List<TbBrand> findAll();

    public PageResult<TbBrand> findPage(int pageNum, int pageSize);

    void add(TbBrand tbBrand);

    void update(TbBrand tbBrand);
    TbBrand findOne(Long id);

    void delete(Long[] ids);

    PageResult<TbBrand> search(TbBrand tbBrand, int page, int rows);
}

