package com.zy.common.exception;

import com.zy.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//自定义异常
@NoArgsConstructor//自动生成无参构造
@AllArgsConstructor//自动生成全部参数构造
@Getter//自动生成getter方法
public class LyException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

}
