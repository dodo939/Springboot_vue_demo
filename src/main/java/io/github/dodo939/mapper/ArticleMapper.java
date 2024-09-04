package io.github.dodo939.mapper;

import com.github.pagehelper.Page;
import io.github.dodo939.pojo.Article;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article(title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            "values(#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, now(), now())")
    void addArticle(Article article);

    Page<Article> listArticle(Integer categoryId, String state, Integer createUser);

    @Select("select * from article where id = #{id} and create_user = #{createUser}")
    Article getArticleById(Integer id, Integer createUser);

    @Update("update article set title = #{article.title}, content = #{article.content}, cover_img = #{article.coverImg}, state = #{article.state}, category_id = #{article.categoryId}, update_time = now() where id = #{article.id} and create_user = #{createUser}")
    void updateArticle(Article article, Integer createUser);

    @Delete("delete from article where id = #{id} and create_user = #{createUser}")
    void deleteArticle(Integer id, Integer createUser);

    @Delete("delete from article where category_id = #{id} and create_user = #{createUser}")
    void deleteArticleByCategoryId(Integer id, Integer createUser);
}
