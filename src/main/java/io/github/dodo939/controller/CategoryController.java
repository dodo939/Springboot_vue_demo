package io.github.dodo939.controller;

import io.github.dodo939.pojo.Category;
import io.github.dodo939.pojo.Result;
import io.github.dodo939.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public Result<Void> addCategory(@RequestBody Category category) {
        if (categoryService.getCategoryByName(category.getCategoryName()) == null) {
            categoryService.addCategory(category);
            return Result.success();
        } else {
            return Result.error("分类名称已存在");
        }
    }

    @GetMapping("/list")
    public Result<List<Category>> listCategory() {
        return Result.success(categoryService.listCategory());
    }

    @GetMapping("/detail")
    public Result<?> getCategoryDetail(@RequestParam("id") Integer id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return Result.error("分类不存在");
        } else {
            return Result.success(category);
        }
    }

    @PutMapping("/update")
    public Result<Void> updateCategory(@RequestBody @Validated(Category.UpdateCategoryValidationGroup.class) Category category) {
        if (categoryService.getCategoryById(category.getId()) == null) {
            return Result.error("分类不存在");
        } else {
            categoryService.updateCategory(category);
            return Result.success();
        }
    }

    @DeleteMapping("/delete")
    public Result<Void> deleteCategory(@RequestParam("id") Integer id) {
        if (categoryService.getCategoryById(id) == null) {
            return Result.error("分类不存在");
        } else {
            categoryService.deleteCategory(id);
            return Result.success();
        }
    }
}
