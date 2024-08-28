package io.github.dodo939.service;

import io.github.dodo939.pojo.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryByName(String categoryName);

    void addCategory(Category category);

    List<Category> listCategory();

    Category getCategoryById(Integer id);

    void updateCategory(Category category);

    void deleteCategory(Integer id);
}
