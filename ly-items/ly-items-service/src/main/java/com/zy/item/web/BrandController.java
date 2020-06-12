package com.zy.item.web;

import com.leyou.item.pojo.Brand;
import com.zy.common.vo.PageResult;
import com.zy.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {
@Autowired
    private BrandService brandService;

    /**
     * 分页查询品牌必填的，false就是不必须的
     * required
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("page")
    //分页查询品牌
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="rows",defaultValue = "5") Integer rows,
            @RequestParam(value="sortBy",required = false) String sortBy,
            @RequestParam(value="desc",defaultValue = "false") Boolean desc,
            @RequestParam(value="key",required = false) String key

    ){
//        PageResult<Brand> result = brandService.queryBrandByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(brandService.queryBrandByPage(page, rows, sortBy, desc, key));
    }
    //新增
      @PostMapping//Void代表无，无返回值，详见vue页面
      //spingmvc自动把字符串转化为list
    public ResponseEntity<Void> saveBrand( @RequestParam("cids")List<Long>cids,Brand brand){
        brandService.saveBrand(cids,brand);
        return  ResponseEntity.status(HttpStatus.CREATED).build();//没有返回值用build
    }
    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand,@RequestParam("cids")List<Long>cids){
        brandService.updateBrand(brand,cids);
        return  ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    /**
     * 删除tb_category_brand中的数据
     * @param bid
     * @return
     */
    @DeleteMapping("cid_bid/{bid}")
    public ResponseEntity<Void> deleteByBrandIdInCategoryBrand(@PathVariable("bid") Long bid){
        //System.out.println("删除中间表");
        this.brandService.deleteByBrandIdInCategoryBrand(bid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除tb_brand中的数据,单个删除、多个删除二合一
     * @param bid
     * @return
     */
    @DeleteMapping("bid/{bid}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("bid") String bid){
        String separator="-";
        if(bid.contains(separator)){
            String[] ids=bid.split(separator);
            for (String id:ids){
                this.brandService.deleteBrand(Long.parseLong(id));
            }
        }
        else {
            this.brandService.deleteBrand(Long.parseLong(bid));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 根据cid查询品牌信息
     * @param cid
     * @return
     */
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCategoryCid(@PathVariable("cid") Long cid){
        return ResponseEntity.ok(brandService.queryBrandByCategoryCid(cid));
    }

    /**
     * 根据品牌id结合，查询品牌信息
     * @param ids
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("ids") List<Long> ids){
        List<Brand> list = this.brandService.queryBrandByBrandIds(ids);
        if (list == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }
}
