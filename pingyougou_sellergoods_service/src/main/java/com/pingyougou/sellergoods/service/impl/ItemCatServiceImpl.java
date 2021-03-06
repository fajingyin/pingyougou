package com.pingyougou.sellergoods.service.impl;
import java.util.List;

import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pingyougou.mapper.TbItemCatMapper;
import com.pingyougou.pojo.TbItemCat;
import com.pingyougou.pojo.TbItemCatExample;
import com.pingyougou.pojo.TbItemCatExample.Criteria;
import com.pingyougou.sellergoods.service.ItemCatService;

import entity.PageResult;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbItemCat> findAll() {
		return itemCatMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbItemCat> page=   (Page<TbItemCat>) itemCatMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbItemCat itemCat) {
		itemCatMapper.insert(itemCat);
        updateCa();
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbItemCat itemCat){
		itemCatMapper.updateByPrimaryKey(itemCat);
        updateCa();
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbItemCat findOne(Long id){
		return itemCatMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {


		for(Long id:ids){

            deleCas(id);
		}
        updateCa();
	}

    private void deleCas(Long id) {
	    itemCatMapper.deleteByPrimaryKey(id);
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        Criteria criteria1 = criteria.andParentIdEqualTo(id);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        if (null != tbItemCats && tbItemCats.size()>0) {
            for (TbItemCat tbItemCat : tbItemCats) {
                deleCas(tbItemCat.getId());
            }
        }


    }

    private void updateCa() {
        List<TbItemCat> list = findAll();
        for (TbItemCat tbItemCat : list) {
            redisTemplate.boundHashOps("itemCat").put(tbItemCat.getName(),tbItemCat.getId());
        }
    }


	@Override
	public List<TbItemCat> findByParentId(Long parentId) {
		TbItemCatExample example = new TbItemCatExample();

		TbItemCatExample.Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);


        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
		return tbItemCats;
	}

	@Override
	public PageResult findPage(TbItemCat itemCat, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		
		if(itemCat!=null){			
						if(itemCat.getName()!=null && itemCat.getName().length()>0){
				criteria.andNameLike("%"+itemCat.getName()+"%");
			}
	
		}
		
		Page<TbItemCat> page= (Page<TbItemCat>)itemCatMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
