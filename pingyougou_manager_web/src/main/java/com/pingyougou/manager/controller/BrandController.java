package com.pingyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import com.pingyougou.pojo.TbBrand;
import com.pingyougou.sellergoods.service.BrandService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author yin
 * @Date 2018/8/10 15:03
 * @Method
 */
//@Controller
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService brandService;

    @RequestMapping(value = "/findAll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<TbBrand> findAll() {
        List<TbBrand> list = brandService.findAll();
        return list;
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int rows) {
        return brandService.findPage(page, rows);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody TbBrand tbBrand) {
        Result result=null;
        try {
            brandService.add(tbBrand);
            result = new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "增加失败");
        }
        return result;
    }

    @RequestMapping("/update")
    public Result update(@RequestBody TbBrand tbBrand) {
        Result result=null;
        try {
            brandService.update(tbBrand);
            result= new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            result=new Result(false,"增加失败");
        }
        return result;
    }

    @RequestMapping("/findOne")
    public TbBrand findOne(Long id){
        TbBrand tbBrand = brandService.findOne(id);
        return tbBrand;
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            brandService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    @RequestMapping("/search")
    public PageResult<TbBrand> search(@RequestBody TbBrand tbBrand,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int rows){
        return brandService.search(tbBrand,page,rows);
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return  brandService.selectOptionList();
    }
}
