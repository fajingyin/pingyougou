package com.pingyougou.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pingyougou.mapper.TbSpecificationOptionMapper;
import com.pingyougou.pojo.TbSpecificationOption;
import com.pingyougou.pojo.TbSpecificationOptionExample;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pingyougou.mapper.TbTypeTemplateMapper;
import com.pingyougou.pojo.TbTypeTemplate;
import com.pingyougou.pojo.TbTypeTemplateExample;
import com.pingyougou.pojo.TbTypeTemplateExample.Criteria;
import com.pingyougou.sellergoods.service.TypeTemplateService;

import entity.PageResult;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

	@Autowired
	private TbTypeTemplateMapper typeTemplateMapper;
	@Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbTypeTemplate> findAll() {
		return typeTemplateMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbTypeTemplate> page=   (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbTypeTemplate typeTemplate) {
		typeTemplateMapper.insert(typeTemplate);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbTypeTemplate typeTemplate){
		typeTemplateMapper.updateByPrimaryKey(typeTemplate);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbTypeTemplate findOne(Long id){
		return typeTemplateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			typeTemplateMapper.deleteByPrimaryKey(id);
		}		
	}

	@Override
	public List<Map> findSpecList(Long id) {
		TbTypeTemplate tbTypeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
        List<Map> list = JSON.parseArray(tbTypeTemplate.getSpecIds(), Map.class);
        for (Map map : list) {
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(new Long((Integer)map.get("id")));
            List<TbSpecificationOption> options = specificationOptionMapper.selectByExample(example);
            map.put("option", options);

        }
        return list;
	}

	@Override
	public List<Map> selectTemplate() {
		return typeTemplateMapper.selectTemplate();
	}

	@Override
	public PageResult findPage(TbTypeTemplate typeTemplate, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbTypeTemplateExample example=new TbTypeTemplateExample();
		Criteria criteria = example.createCriteria();
		
		if(typeTemplate!=null){			
						if(typeTemplate.getName()!=null && typeTemplate.getName().length()>0){
				criteria.andNameLike("%"+typeTemplate.getName()+"%");
			}
			if(typeTemplate.getSpecIds()!=null && typeTemplate.getSpecIds().length()>0){
				criteria.andSpecIdsLike("%"+typeTemplate.getSpecIds()+"%");
			}
			if(typeTemplate.getBrandIds()!=null && typeTemplate.getBrandIds().length()>0){
				criteria.andBrandIdsLike("%"+typeTemplate.getBrandIds()+"%");
			}
			if(typeTemplate.getCustomAttributeItems()!=null && typeTemplate.getCustomAttributeItems().length()>0){
				criteria.andCustomAttributeItemsLike("%"+typeTemplate.getCustomAttributeItems()+"%");
			}
	
		}
		
		Page<TbTypeTemplate> page= (Page<TbTypeTemplate>)typeTemplateMapper.selectByExample(example);
        saveToRedis();
		return new PageResult(page.getTotal(), page.getResult());
	}

	private void saveToRedis() {
        List<TbTypeTemplate> typeTemplateList = findAll();
        for (TbTypeTemplate tbTypeTemplate : typeTemplateList) {
            List<Map> brandList = JSON.parseArray(tbTypeTemplate.getBrandIds(),Map.class);
            redisTemplate.boundHashOps("brandList").put(tbTypeTemplate.getId(),brandList);
            List<Map> specList = findSpecList(tbTypeTemplate.getId());
            redisTemplate.boundHashOps("specList").put(tbTypeTemplate.getId(),specList);
        }
    }
	
}
