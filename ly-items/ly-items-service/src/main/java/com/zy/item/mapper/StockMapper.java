package com.zy.item.mapper;

import com.leyou.item.pojo.Stock;
import com.zy.common.mapper.BaseMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;

public interface StockMapper extends InsertListMapper<Stock>,BaseMapper<Stock> {
}
