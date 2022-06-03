package ironyang.tobyspring.user.service;

import ironyang.tobyspring.user.dao.UserDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
}
