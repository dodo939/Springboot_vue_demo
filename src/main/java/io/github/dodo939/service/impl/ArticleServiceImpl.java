package io.github.dodo939.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.github.dodo939.mapper.ArticleMapper;
import io.github.dodo939.pojo.Article;
import io.github.dodo939.pojo.PageBean;
import io.github.dodo939.service.ArticleService;
import io.github.dodo939.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserService userService;

    @Override
    public void addArticle(Article article) {
        Integer createUser = userService.getCurrentUserId();
        article.setCreateUser(createUser);
        articleMapper.addArticle(article);
    }

    @Override
    public PageBean<Article> listArticle(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        PageBean<Article> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        Integer createUser = userService.getCurrentUserId();
        Page<Article> page = articleMapper.listArticle(categoryId, state, createUser);
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    @Override
    public Article getArticleById(Integer id) {
        Integer createUser = userService.getCurrentUserId();
        return articleMapper.getArticleById(id, createUser);
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
    }

    @Override
    public void deleteArticle(Integer id) {
        articleMapper.deleteArticle(id);
    }
}
