package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Data
@Table(name = "tb_spec_group")//表示对应数据库中的表
public class SpecGroup {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long cid;//商品分类
    private String name;//名称

    @Transient
    private List<SpecParam> params;
}
