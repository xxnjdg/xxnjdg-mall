package io.xxnjdg.mall.product.constant;

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
}
