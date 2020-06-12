package com.zy.item.web;

import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.zy.common.vo.PageResult;
import com.zy.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    /**
     * 分页查询spu
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>>querySpuPage(     @RequestParam(value="page",defaultValue = "1") Integer page,
                                                            @RequestParam(value="rows",defaultValue = "5") Integer rows,
                                                            @RequestParam(value="saleable",required = false) Boolean saleable,
                                                            @RequestParam(value="key",required = false) String key){
        return ResponseEntity.ok(goodsService.querySpuPage(page,rows,saleable,key));
    }
    //新增商品

    /**
     * 这里因为参数蛇json对象所以加下面的注解
     * @param spu
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void>saveGoods(@RequestBody Spu spu){
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据spu的id查询spudetail的信息
     * @param id
     * @return
     */
    //其实这里的id是spu的id
    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> querySpuDetailByid(@PathVariable("id")Long id){

        return ResponseEntity.ok(goodsService.querySpuDetailByid(id));
    }

    /**
     * 根据spuid查询sku的所有数据
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>>querySkuList(@RequestParam("id")Long spuId){
        return ResponseEntity.ok(goodsService.querySkuList(spuId));
    }
}
