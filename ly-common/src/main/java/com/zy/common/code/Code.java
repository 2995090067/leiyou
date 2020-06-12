package com.zy.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  Code {
    UPDATE_OK("修改成功"),
    DELETE_OK("删除成功"),
    SELECT_OK("查询成功"),
    INSERT_OK("添加成功")
    ;
    private String msg;
}
