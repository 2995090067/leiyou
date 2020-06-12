package com.zy.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.item.pojo.*;
import com.zy.common.enums.ExceptionEnum;
import com.zy.common.exception.LyException;
import com.zy.common.vo.PageResult;
import com.zy.item.mapper.SkuMapper;
import com.zy.item.mapper.SpuDetailMapper;
import com.zy.item.mapper.SpuMapper;
import com.zy.item.mapper.StockMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private  CategoryService categoryService;
    @Autowired
    private  BrandService brandService;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    public PageResult<Spu> querySpuPage(Integer page, Integer rows, Boolean saleable, String key) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Spu.class);
        Example.Criteria criteria=example.createCriteria();
        //搜索字段过滤
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        //上下架过滤
        if(saleable !=null){
            criteria.andEqualTo("saleable",saleable);
        }
        //默认排序，商品更新时间，其实前端没让我们排序，不过默认
        example.setOrderByClause("last_update_time DESC");//倒序 也可 lastUpdateTime
        //查询
        List<Spu> spus = spuMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(spus)){
            throw  new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        //解析分类和品牌的名称
        loadCateAndBrandName(spus);
        //解析一下分页结果
        PageInfo<Spu>info =new PageInfo<>(spus);
        return  new PageResult<>(info.getTotal(),spus);
    }

    private void loadCateAndBrandName(List<Spu> spus) {
        //spus.for 遍历
        for (Spu spu : spus) {
            //1.1处理分类名称 三级分类
            //Stream流java8的新特性
            List<String> names = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());//取完变成名称的流,zai.collecti变成name的集合了
            spu.setCname(StringUtils.join(names,"/"));//把集合分割为字符串已以/分割（其实是添加了“/”的字符串）
            //2.处理品牌名称  Stream
            spu.setBname(brandService.queryById(spu.getBrandId()).getName());

        }
    }
    //四张表要添加
    @Transactional(rollbackFor = Exception.class)
    public void saveGoods(Spu spu) {
        //新增spu
        spu.setId(null);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false );
        int count = spuMapper.insert(spu);
        if(!(count>0)){
            throw  new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
        //新增detail
        SpuDetail detail=spu.getSpuDetail();
        detail.setSpuId(spu.getId());
        spuDetailMapper.insert(detail);
        //定义库存新增
        List<Stock>stockList=new ArrayList<>();
        //新增sku
        List<Sku>skus=spu.getSkus();
        for (Sku sku : skus) {
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());
//要么批量新增查id，要么不动
             count = skuMapper.insert(sku);
            if(!(count>0)){
                throw  new LyException(ExceptionEnum.GOODS_SAVE_ERROR);

            }
            //新增库存
            Stock stock=new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockList.add(stock);
        }
        //批量新增库存
         count=stockMapper.insertList(stockList);
        if(!(count>0)){
            throw  new LyException(ExceptionEnum.GOODS_SAVE_ERROR);

        }

    }
    //因为这个id是detail的主键，所以可以用通用mapper的查询主键功能
    public SpuDetail querySpuDetailByid(Long id) {
        SpuDetail detail = spuDetailMapper.selectByPrimaryKey(id);
        if(detail==null) {
                throw new LyException(ExceptionEnum.SPU_DETAIL_NOT_FOUND);
        }
        return detail;
    }

    public List<Sku> querySkuList(Long spuId) {
        //查询sku
        Sku sku=new Sku();
        sku.setSpuId(spuId);
        List<Sku> select = skuMapper.select(sku);
        if(CollectionUtils.isEmpty(select)){
            throw new LyException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }
        //查询库存
//        for (Sku sku1 : select) {
//            Stock stock = stockMapper.selectByPrimaryKey(sku1.getId());
//            if(stock==null){
//                throw new LyException(ExceptionEnum.STOCK_NOT_FOUND);
//            }
//            stock.setStock(stock.getStock());
//        }
        //查询库存
        //map的k是库存的id,map的值是库存的值
        // 我们把stock变成map,Key是库存的id,值是库存的值
        List<Long>ids=select.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock>stockList=stockMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(stockList)){
            throw new LyException(ExceptionEnum.STOCK_NOT_FOUND);
        }
        Map<Long, Integer> stockMap = stockList.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        select.forEach(s -> s.setStock(stockMap.get(s.getId())));
        return select;
    }
}
