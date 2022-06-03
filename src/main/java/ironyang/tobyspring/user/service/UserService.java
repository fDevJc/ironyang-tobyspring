package ironyang.tobyspring.user.service;

import ironyang.tobyspring.user.dao.UserDao;
import ironyang.tobyspring.user.domain.Level;
import ironyang.tobyspring.user.domain.Users;
import ironyang.tobyspring.user.service.levelupgradepolicy.UserLevelUpgradePolicy;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final UserLevelUpgradePolicy userLevelUpgradePolicy;

    public void upgradeLevels() throws SQLException, ClassNotFoundException {
        List<Users> users = userDao.getAll();
        for (Users user : users) {
            if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                userLevelUpgradePolicy.upgradeLevel(user);
                userDao.update(user);
            }
        }
    }

    public void add(Users user) throws SQLException, ClassNotFoundException {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
