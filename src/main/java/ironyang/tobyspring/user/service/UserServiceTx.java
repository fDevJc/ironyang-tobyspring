package ironyang.tobyspring.user.service;

import ironyang.tobyspring.user.domain.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class UserServiceTx implements UserService{
    private final UserService userService;

    @Override
    public void upgradeLevels() throws SQLException, ClassNotFoundException {
        log.info("[upgradeLevels]transaction start");
        userService.upgradeLevels();
        log.info("[upgradeLevels]transaction end");
    }

    @Override
    public void add(Users user) throws SQLException, ClassNotFoundException {
        log.info("[add]transaction start");
        userService.add(user);
        log.info("[add]transaction end");
    }
}
