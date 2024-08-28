package io.github.dodo939.mapper;

import com.github.pagehelper.Page;
import io.github.dodo939.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article(title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            "values(#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, now(), now())")
    void addArticle(Article article);

    Page<Article> listArticle(Integer categoryId, String state, Integer createUser);
}
