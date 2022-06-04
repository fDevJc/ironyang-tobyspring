package ironyang.tobyspring.user.service;

import ironyang.tobyspring.user.dao.UserDao;
import ironyang.tobyspring.user.domain.Level;
import ironyang.tobyspring.user.domain.Users;
import ironyang.tobyspring.user.service.levelupgradepolicy.UserLevelUpgradePolicy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserDao userDao;
    private final UserLevelUpgradePolicy userLevelUpgradePolicy;

    @Override
    public void upgradeLevels() throws SQLException, ClassNotFoundException {
        log.info("transaction start");

        upgradeLevelsInternal();

        log.info("transaction end");
    }

    private void upgradeLevelsInternal() throws SQLException, ClassNotFoundException {
        List<Users> users = userDao.getAll();
        for (Users user : users) {
            if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                userLevelUpgradePolicy.upgradeLevel(user);
                userDao.update(user);
            }
        }
    }

    @Override
    public void add(Users user) throws SQLException, ClassNotFoundException {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
