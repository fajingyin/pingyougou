package com.pingyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pingyougou.mapper.TbBrandMapper;
import entity.PageResult;
import com.pingyougou.pojo.TbBrand;
import com.pingyougou.pojo.TbBrandExample;
import com.pingyougou.sellergoods.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author yin
 * @Date 2018/8/11 22:09
 * @Method
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private TbBrandMapper tbBrandMapper;

    @Override
    public List<TbBrand> findAll() {
        return tbBrandMapper.selectByExample(null);
    }

    @Override
    public PageResult<TbBrand> findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbBrand> page = (Page<TbBrand>)tbBrandMapper.selectByExample(null);
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public void add(TbBrand tbBrand) {
        tbBrandMapper.insert(tbBrand);
    }

    @Override
    public void update(TbBrand tbBrand) {
        tbBrandMapper.updateByPrimaryKey(tbBrand);
    }

    @Override
    public TbBrand findOne(Long id){
        TbBrand tbBrand = tbBrandMapper.selectByPrimaryKey(id);
        return tbBrand;
    }

    @Override
    public void delete(Long[] ids) {
        TbBrandExample example = new TbBrandExample();
        TbBrandExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(Arrays.asList(ids));
        tbBrandMapper.deleteByExample(example);

    }

  /*  @Override
    public PageResult findPage(TbBrand brand, int pageNum, int pageSize) {
        return null;
    }
*/
    @Override
    public PageResult<TbBrand> search(TbBrand tbBrand, int page, int rows) {
        PageHelper.startPage(page,rows);
        TbBrandExample example = new TbBrandExample();
       TbBrandExample.Criteria criteria = example.createCriteria();
        if (tbBrand != null) {
            if(StringUtils.isNotBlank(tbBrand.getFirstChar())){
                criteria.andFirstCharEqualTo(tbBrand.getFirstChar());
            }
            if(StringUtils.isNotBlank(tbBrand.getName())){
                criteria.andNameLike("%"+tbBrand.getName()+"%");
            }
        }
       Page<TbBrand> pages = (Page<TbBrand>) tbBrandMapper.selectByExample(example);

        return new PageResult<>(pages.getTotal(),pages.getResult());
    }

    @Override
    public List<Map> selectOptionList() {

        return tbBrandMapper.selectOptionList();
    }


}
