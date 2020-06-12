package com.zy.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnum
{
    PRICE_CANNOT_BE_NULL(400,"价格不能为空!"),
    CATEGORY_NOT_FOND(404,"查询不到商品分类"),
    BRAND_NOT_FOUND (404,"品牌不存在"),
    Brand_CREATED_ERROR (400,"品牌新增失败！"),
    SPU_NOT_FOUND(404,"商品不存在"),
    SPU_DETAIL_NOT_FOUND(404,"商品详情不存在"),
//    Brand_CREATED_MIDDLE_ERROR (500,"新增品牌中间表失败！"),
    UPLOAD_FILE_ERROR (400,"文件上传失败！"),
    FILE_TYPE_MSMATCH(400,"文件类型不匹配"),
    CATEGORY_NOT_FOUND(400,"分类创建失败"),
    GOODS_SAVE_ERROR(400,"新增商品异常"),
    STOCK_UPDATE_ERROR(400,"库存修改异常"),
    GOODS_SKU_NOT_FOUND(404,"查询不到商品库存"),
    GOODS_NOT_FOUND (404,"商品查询不到"),
    SPEC_GROUP_NOT_FOUND (404,"规格组查询不到"),
    SPEC_PARAM_NOT_FOUND (404,"规格参数没有找到"),
    SPEC_GROUP_SAVE_ERROR(400,"规格组保存失败"),
    SPEC_PRARM_UPDATE_ERROR(400,"规格参数修改失败"),
    SPEC_PRARM_DELETE_ERROR(400,"规格参数删除异常"),
    INSERT_GROUP_BYNAME_ERROR (400,"创建规格表失败"),
    DELECT_GROUP_BYID_ERROR(400,"删除规格参数表的分组异常"),
    UPLOAD_GROUP_BYID_ERROR(400,"规格参数修改异常"),
    INSERT_SPEC_PARAM_ERROR(400,"规格参数添加失败"),
    DELECT_SPECPARAM_BYID_ERROR(400,"规格参数删除失败"),
    STOCK_NOT_FOUND(404,"STOCK查询不到"),
    INSERT_SPU_ERROR(400,"spu添加失败！");
    ;



    private int code;
    private String msg;
}
