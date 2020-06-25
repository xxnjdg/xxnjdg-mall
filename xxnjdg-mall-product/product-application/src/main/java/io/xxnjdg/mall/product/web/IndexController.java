package io.xxnjdg.mall.product.web;

import io.xxnjdg.mall.product.entity.CategoryEntity;
import io.xxnjdg.mall.product.service.CategoryService;
import io.xxnjdg.mall.product.vo.Catelog2VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/18 18:00
 */
@Controller
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        // TODO 1 查出所有的1级分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categories();
        // 视图解析器进行拼串
        // classpath:/templates/ + result + .html
        model.addAttribute("categories", categoryEntities);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catelog2VO>> getCatalogJson()  {
        return categoryService.getCatalogJson();
    }
}
