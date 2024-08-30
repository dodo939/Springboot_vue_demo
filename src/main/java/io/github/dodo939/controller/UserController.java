package io.github.dodo939.controller;

import io.github.dodo939.pojo.Result;
import io.github.dodo939.pojo.User;
import io.github.dodo939.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<Void> register(@Pattern(regexp = "^[a-zA-Z0-9_]{5,16}$") String username, @Pattern(regexp = "^[a-zA-Z0-9_]{5,16}$") String password) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return Result.error("用户名已存在");
        } else {
            userService.register(username, password);
            return Result.success();
        }
    }

    @PostMapping("/login")
    public Result<?> login(@Pattern(regexp = "^[a-zA-Z0-9_]{5,16}$") String username, @Pattern(regexp = "^[a-zA-Z0-9_]{5,16}$") String password) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return Result.error("用户名不存在");
        }

        if (userService.checkPassword(user, password)) {
            String token = userService.generateToken(user);
            String key = "big-event:tokens:" + user.getId() + ":" + token;
            // 设置 token 缓存时间为 24 小时
            redisTemplate.opsForValue().set(key, 1, 24, TimeUnit.HOURS);
            return Result.success(token);
        } else {
            return Result.error("密码错误");
        }
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        User user = userService.getCurrentUser();
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody @Validated User user) {
        userService.updateUser(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result<Void> updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result<Void> updatePwd(@RequestBody Map<String, String> params) {
        return userService.updatePassword(params);
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        String key = "big-event:tokens:" + userService.getCurrentUserId() + ":" + token;
        redisTemplate.delete(key);
        return Result.success();
    }
}
