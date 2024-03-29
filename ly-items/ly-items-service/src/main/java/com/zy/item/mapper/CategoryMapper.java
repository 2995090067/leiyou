package com.zy.item.mapper;

import com.leyou.item.pojo.Category;
import com.zy.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 根据品牌id查询商品分类  由于要求查中间表
     * @param bid
     * @return
     */
    @Select("SELECT * FROM tb_category WHERE id IN (SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid})")
    List<Category> queryByBrandId(Long bid);
    /**
     * 根据category id删除中间表相关数据
     * @param cid
     */
    @Delete("DELETE FROM tb_category_brand WHERE category_id = #{cid}")
    void deleteByCategoryIdInCategoryBrand(@Param("cid") Long cid);

   /**
     * 根据id查名字
   * @param id
    * @return
    */
   @Select("SELECT name FROM tb_category WHERE id = #{id}")
   String queryNameById(Long id);

   /**
   * 查询最后一条数据
   * @return
    */
   @Select("SELECT * FROM `tb_category` WHERE id = (SELECT MAX(id) FROM tb_category)")
   List<Category> selectLast();

}
