package com.zy.common.advice;

import com.zy.common.enums.ExceptionEnum;
import com.zy.common.exception.LyException;
import com.zy.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CommonExceptionGandler {
    @ExceptionHandler(LyException.class)
    public ResponseEntity<ExceptionResult> handleException(LyException e){
        ExceptionEnum em=e.getExceptionEnum();
        return ResponseEntity.status(em.getCode()).body(new ExceptionResult(e.getExceptionEnum()));
    }

}
