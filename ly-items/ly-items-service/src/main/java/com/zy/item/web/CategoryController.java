package com.zy.item.web;

import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.zy.common.enums.ExceptionEnum;
import com.zy.common.exception.LyException;
import com.zy.item.service.BrandService;
import com.zy.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;
    /**
     * 根据父节点id查询商品分类
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam("pid") Long pid) {

        return ResponseEntity.ok(categoryService.queryCategoryListByPid(pid));//简写成共返回200
//        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    /**
     * 通过品牌id查询商品分类
     */
    @GetMapping("bid/{bid}")
    public  ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid") Long bid){
       List<Category> list=this.categoryService.queryByBrandId(bid);
       if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
       }
        return ResponseEntity.ok(list);
    }




    /**
     * 保存
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveCategory(Category category){
        this.categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更新
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateCategory(Category category){
        this.categoryService.updateCategory(category);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * 删除
     * @return
     */
    @DeleteMapping("cid/{cid}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("cid") Long id){
        this.categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 根据分类id集合查询分类名称
     * @param ids
     * @return
     */
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids")List<Long> ids){
        List<String> list = categoryService.queryNameByIds(ids);
        if (list == null || list.size() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(list);
        }
    }

    /**
     * 根据分类id集合查询分类名称
     * @param ids
     * @return
     */
    @GetMapping("all")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids")List<Long> ids){
        List<Category> list = categoryService.queryCategoryByIds(ids);
        if (list == null || list.size() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(list);
        }
    }

    /**
     * 根据分类id集合查询分类名称
     * @param id
     * @return
     */
    @GetMapping("all/level/{cid3}")
    public ResponseEntity<List<Category>> queryAllCategoryLevelByCid3(@PathVariable("cid3")Long id){
        List<Category> list = categoryService.queryAllCategoryLevelByCid3(id);
        if (list == null || list.size() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(list);
        }
    }
}
