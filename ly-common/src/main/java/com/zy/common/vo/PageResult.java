package com.zy.common.vo;

import lombok.Data;

import java.util.List;

//view object 视图对象 返回给前段页面的对象，而bo数据库隐私性高最好别暴露给前端
@Data
public class PageResult<T> {
    private Long total;// 总条数
    private Long totalPage;// 总页数
    private List<T> items;// 当前页数据

    public PageResult() {
    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Long totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

}
