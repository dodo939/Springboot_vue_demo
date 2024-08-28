package io.github.dodo939.service.impl;

import io.github.dodo939.mapper.CategoryMapper;
import io.github.dodo939.pojo.Category;
import io.github.dodo939.service.CategoryService;
import io.github.dodo939.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserService userService;

    @Override
    public Category getCategoryByName(String categoryName) {
        Integer userId = userService.getCurrentUserId();
        return categoryMapper.getCategoryByName(categoryName, userId);
    }

    @Override
    public void addCategory(Category category) {
        Integer userId = userService.getCurrentUserId();
        category.setCreateUser(userId);
        categoryMapper.addCategory(category);
    }

    @Override
    public List<Category> listCategory() {
        Integer userId = userService.getCurrentUserId();
        return categoryMapper.listCategory(userId);
    }

    @Override
    public Category getCategoryById(Integer id) {
        Integer userId = userService.getCurrentUserId();
        return categoryMapper.getCategoryById(id, userId);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateCategory(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryMapper.deleteCategory(id);
    }
}
