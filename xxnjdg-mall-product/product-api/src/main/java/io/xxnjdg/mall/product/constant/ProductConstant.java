package io.xxnjdg.mall.product.constant;

import lombok.Getter;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/14 15:39
 */
public class ProductConstant {


    public enum  AttrEnum{
        ATTR_TYPE_BASE(1,"基本属性"),ATTR_TYPE_SALE(0,"销售属性");
        private int code;
        private String msg;

        AttrEnum(int code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    @Getter
    public enum StatusEnum {

        SPU_NEW(0, "新建"),

        SPU_UP(1, "上架"),

        SPU_DOWN(2, "下架");

        private int code;

        private String message;

        StatusEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
