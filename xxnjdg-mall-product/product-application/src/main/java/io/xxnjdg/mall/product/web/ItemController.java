package io.xxnjdg.mall.product.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/25 10:59
 */
@Controller
public class ItemController {

    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId){
        return "item";
    }

}
