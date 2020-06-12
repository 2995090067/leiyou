package com.zy.item.service;

import com.leyou.item.pojo.Category;
import com.zy.common.enums.ExceptionEnum;
import com.zy.common.exception.LyException;
import com.zy.item.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryCategoryListByPid(Long pid) {
        Category t=new Category();
        t.setParentId(pid);
        List<Category> list = categoryMapper.select(t);//里面传对象，对象需要直接new出来，把对象中非空字段作为条件参数
        if (CollectionUtils.isEmpty(list)){//判断返回值是否为空
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        return list;
    }
//    查询三级分类的结果
    public List<Category>queryByIds(List<Long>ids){
        List<Category> list = categoryMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return list;
    }
    public List<Category> queryByBrandId(Long bid) {
        return this.categoryMapper.queryByBrandId(bid);
    }
    public void saveCategory(Category category) {
        /**
         * 将本节点插入到数据库中
         * 将此category的父节点的isParent设为true
         */

        //1.首先置id为null
        category.setId(null);
        //2.保存
        this.categoryMapper.insert(category);
        //3.修改父节点
        Category parent = new Category();
        parent.setId(category.getParentId());
        parent.setIsParent(true);
        this.categoryMapper.updateByPrimaryKeySelective(parent);

    }

    /**
     * 更新
     * @param category
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(Category category) {
        this.categoryMapper.updateByPrimaryKeySelective(category);
    }
    //删除
    public void deleteCategory(Long id) {
        /**
         * 先根据id查询要删除的对象，然后进行判断
         * 如果是父节点，那么删除所有附带子节点,然后维护中间表
         * 如果是子节点，那么只删除自己,然后判断父节点孩子的个数，如果孩子不为0，则不做修改；如果孩子个数为0，则修改父节点isParent
         * 的值为false,最后维护中间表
         */
        Category category=this.categoryMapper.selectByPrimaryKey(id);
        if(category.getIsParent()){
            //1.查找所有叶子节点
            List<Category> list = new ArrayList<>();
            queryAllLeafNode(category,list);

            //2.查找所有子节点
            List<Category> list2 = new ArrayList<>();
            queryAllNode(category,list2);

            //3.删除tb_category中的数据,使用list2
            for (Category c:list2){
                this.categoryMapper.delete(c);
            }

            //4.维护中间表
            for (Category c:list){
                this.categoryMapper.deleteByCategoryIdInCategoryBrand(c.getId());
            }

        }else {
            //1.查询此节点的父亲节点的孩子个数 ===> 查询还有几个兄弟
            Example example = new Example(Category.class);
            example.createCriteria().andEqualTo("parentId",category.getParentId());
            List<Category> list=this.categoryMapper.selectByExample(example);
            if(list.size()!=1){
                //有兄弟,直接删除自己
                this.categoryMapper.deleteByPrimaryKey(category.getId());

                //维护中间表
                this.categoryMapper.deleteByCategoryIdInCategoryBrand(category.getId());
            }
            else {
                //已经没有兄弟了
                this.categoryMapper.deleteByPrimaryKey(category.getId());

                Category parent = new Category();
                parent.setId(category.getParentId());
                parent.setIsParent(false);
                this.categoryMapper.updateByPrimaryKeySelective(parent);
                //维护中间表
                this.categoryMapper.deleteByCategoryIdInCategoryBrand(category.getId());
            }
        }
    }

    /**
     * 根据ids查询名字
     * @param asList
     * @return
     */
    public List<String> queryNameByIds(List<Long> asList) {
        List<String> names = new ArrayList<>();
        if (asList != null && asList.size() !=0){
            for (Long id : asList) {
                names.add(this.categoryMapper.queryNameById(id));
            }
        }
        return names;
        //使用通用mapper接口中的SelectByIdListMapper接口查询
        //return this.categoryMapper.selectByIdList(asList).stream().map(Category::getName).collect(Collectors.toList());
    }

    /**
     * 查询数据库中最后一条数据
     * @return
     */
    public List<Category> queryLast() {
        List<Category> last =this.categoryMapper.selectLast();
        return last;
    }

    /**
     * 根据分类id集合查询分类信息
     * @param ids
     * @return
     */
    public List<Category> queryCategoryByIds(List<Long> ids) {
        return this.categoryMapper.selectByIdList(ids);
    }

    /**
     * 根据cid3查询其所有层级分类
     * @param id
     * @return
     */
    public List<Category> queryAllCategoryLevelByCid3(Long id) {
        List<Category> categoryList = new ArrayList<>();
        Category category = this.categoryMapper.selectByPrimaryKey(id);
        while (category.getParentId() != 0){
            categoryList.add(category);
            category = this.categoryMapper.selectByPrimaryKey(category.getParentId());
        }
        categoryList.add(category);
        return categoryList;
    }

    /**
     * 查询本节点下所包含的所有叶子节点，用于维护tb_category_brand中间表
     * @param category
     * @param leafNode
     */
    private void queryAllLeafNode(Category category,List<Category> leafNode){
        if(!category.getIsParent()){
            leafNode.add(category);
        }
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId",category.getId());
        List<Category> list=this.categoryMapper.selectByExample(example);

        for (Category category1:list){
            queryAllLeafNode(category1,leafNode);
        }
    }

    /**
     * 查询本节点下所有子节点
     * @param category
     * @param node
     */
    private void queryAllNode(Category category,List<Category> node){

        node.add(category);
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId",category.getId());
        List<Category> list=this.categoryMapper.selectByExample(example);

        for (Category category1:list){
            queryAllNode(category1,node);
        }
    }

}
