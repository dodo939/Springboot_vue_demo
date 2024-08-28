package io.github.dodo939.controller;

import io.github.dodo939.pojo.Article;
import io.github.dodo939.pojo.PageBean;
import io.github.dodo939.pojo.Result;
import io.github.dodo939.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/add")
    public Result<Void> addArticle(@RequestBody @Validated Article article) {
        articleService.addArticle(article);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<PageBean<Article>> listArticle(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {
        return Result.success(articleService.listArticle(pageNum, pageSize, categoryId, state));
    }

    @GetMapping("/detail")
    public Result<?> getArticleDetail(@RequestParam Integer id) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return Result.error("文章不存在");
        }
        return Result.success(article);
    }

    @PutMapping("/update")
    public Result<Void> updateArticle(@RequestBody @Validated Article article) {
        if (articleService.getArticleById(article.getId()) == null) {
            return Result.error("文章不存在");
        } else {
            articleService.updateArticle(article);
            return Result.success();
        }
    }

    @DeleteMapping("/delete")
    public Result<Void> deleteArticle(@RequestParam Integer id) {
        if (articleService.getArticleById(id) == null) {
            return Result.error("文章不存在");
        } else {
            articleService.deleteArticle(id);
            return Result.success();
        }
    }
}
