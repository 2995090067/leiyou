package com.zy.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.item.pojo.Brand;
import com.zy.common.enums.ExceptionEnum;
import com.zy.common.exception.LyException;
import com.zy.common.vo.PageResult;
import com.zy.item.mapper.BrandMapper;
import jdk.management.resource.ResourceType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        //分页助手，脱离通用mapper也能用，当前页面，页面大小
//分页助手利用mybatis拦截器对接下来的sql，自动在他后面添上limt语句
        PageHelper.startPage(page,rows);
        /**
         * where 'name' like "%XX%" or letter=='xx'
         *order by id desc
         */
        // 条件的过滤
        Example example = new Example(Brand.class);//对象的字节码
        if(StringUtils.isNotBlank(key)){
            //只要不为空就可以过滤
            //过滤条件
            //创建一个标准条件
            example.createCriteria().orLike("name","%"+key+"%")
                    .orEqualTo("letter",key.toUpperCase());
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            String orderByClause=sortBy+" "+(desc ? "DESC" : "ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        List<Brand> list = brandMapper.selectByExample(example);//已经包含总条数
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        //解析分页结果
        PageInfo<Brand> info=new PageInfo<>(list);
        return new PageResult<>(info.getTotal(),list);//总数，数据
  }
    @Transactional(rollbackFor = Exception.class)
  public void saveBrand( List<Long> cids,Brand brand) {
       //新增
       brand.setId(null);
      int count = brandMapper.insert(brand);
    if(count !=1){
           throw new LyException(ExceptionEnum.Brand_CREATED_ERROR );
     }
      //新增中间表
      for(Long cid:cids){
           count=brandMapper.insertCateGoryBrand(cid,brand.getId());
//          System.out.println(cid);
          if(count !=1){
             throw new LyException(ExceptionEnum.Brand_CREATED_ERROR  );
          }
       }
   }

    /**
     * 修改的
     * @param brand
     * @param cids
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(Brand brand, List<Long> cids) {
        //删除原来的数据
        int count = brandMapper.deleteCategoryBrand(brand.getId());
        if(count!=1){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        count = brandMapper.updateByPrimaryKeySelective(brand);
        if(count!=1){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        for (Long cid : cids) {
            count = brandMapper.insertCateGoryBrand(cid,brand.getId());
            if(count!=1){
                throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
            }
        }
    }
    /**
     * 品牌删除
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBrand(Long id) {
        //删除品牌信息
        this.brandMapper.deleteByPrimaryKey(id);

        //维护中间表
        this.brandMapper.deleteCategoryBrand(id);
    }


    /**
     * 删除中间表中的数据
     * @param bid
     */
    public void deleteByBrandIdInCategoryBrand(Long bid) {
        this.brandMapper.deleteCategoryBrand(bid);
    }

    /**
     * 根据 Cid查询brand
     * @param cid
     * @return
     */
    public List<Brand> queryBrandByCategoryCid(Long cid) {
        List<Brand> brands = brandMapper.queryBrandByCategoryId(cid);
        if(CollectionUtils.isEmpty(brands)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brands;
    }

    /**
     * 根据品牌id集合查询品牌信息
     * @param ids
     * @return
     */
    public List<Brand> queryBrandByBrandIds(List<Long> ids) {
        return this.brandMapper.selectByIdList(ids);
    }
    public Brand queryById(Long id){
        Brand brand = brandMapper.selectByPrimaryKey(id);
        if(brand==null){
            throw  new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brand;
    }
}
