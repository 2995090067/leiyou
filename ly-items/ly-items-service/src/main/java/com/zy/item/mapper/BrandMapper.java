package com.zy.item.mapper;

import com.leyou.item.pojo.Brand;
import com.zy.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BrandMapper extends BaseMapper<Brand> {
   //中间表的添加
    @Insert("INSERT INTO tb_category_brand (category_id,brand_id) VALUES (#{cid},#{bid})")
    int insertCateGoryBrand(@Param("cid")Long cid,@Param("bid")Long bid);
//    @Select("SELECT b.id,b.name,b.letter,b.image FROM tb_brand b,tb_category_brand cb WHERE b.id=cb.brand_id and cb.category_id=#{cid}")
//    List<Brand> queryBrandByCid(@Param("cid")Long cid);
 /**
  * 根据brand id删除中间表相关数据
  * @param bid
  */
    @Delete("delete from tb_category_brand where brand_id=#{bid}")
    int deleteCategoryBrand(Long bid);

  /**
   * 根据category id查询brand,左连接
   * @param cid
   * @return
   */
    @Select("SELECT b.* FROM tb_category_brand cb INNER JOIN tb_brand b ON b.id=cb.brand_id WHERE cb.category_id=#{cid}")
    List<Brand> queryBrandByCategoryId(@Param("cid") Long cid);



}
