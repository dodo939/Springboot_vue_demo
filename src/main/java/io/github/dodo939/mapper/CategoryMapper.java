package io.github.dodo939.mapper;

import io.github.dodo939.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("select * from category where category_name = #{categoryName} and create_user = #{createUser}")
    Category getCategoryByName(String categoryName, Integer createUser);

    @Select("select * from category where id = #{id} and create_user = #{createUser}")
    Category getCategoryById(Integer id, Integer createUser);

    @Select("select * from category where create_user = #{createUser}")
    List<Category> listCategory(Integer createUser);

    @Insert("insert into category (category_name, category_alias, create_user, create_time, update_time) " +
            "values (#{categoryName}, #{categoryAlias}, #{createUser}, now(), now())")
    void addCategory(Category category);

    @Update("update category set category_name = #{category.categoryName}, category_alias = #{category.categoryAlias}, update_time = now() where id = #{category.id} and create_user = #{createUser}")
    void updateCategory(Category category, Integer createUser);

    @Delete("delete from category where id = #{id} and create_user = #{createUser}")
    void deleteCategory(Integer id, Integer createUser);
}
