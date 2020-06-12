package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tb_spec_param")
public class SpecParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private Long groupId;
    private String name;
    @Column(name = "`numeric`") //转义字符，防止是关键字，实际上也是关键字
    private Boolean numeric;//数形
    private String unit;//单元
    private Boolean generic;//属
    private Boolean searching;//搜索
    private String segments;

}