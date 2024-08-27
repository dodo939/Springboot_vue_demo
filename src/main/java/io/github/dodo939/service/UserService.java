package io.github.dodo939.service;

import io.github.dodo939.pojo.Result;
import io.github.dodo939.pojo.User;

import java.util.Map;

public interface UserService {

    User getUserByUsername(String username);

    void register(String username, String md5Password);

    Boolean checkPassword(User user, String password);

    Boolean checkPassword(String password);

    String generateToken(User user);

    User getCurrentUser();

    void updateUser(User user);

    void updateAvatar(String avatarUrl);

    Result<?> updatePassword(Map<String, String> params);
}
