package io.github.dodo939.service;

import io.github.dodo939.pojo.User;

public interface UserService {

    User getUserByUsername(String username);

    void register(String username, String md5Password);

    Boolean checkPassword(User user, String password);

    String generateToken(User user);

    User getCurrentUser();

    void updateUser(User user);
}
