package ironyang.tobyspring.user.service.levelupgradepolicy;

import ironyang.tobyspring.user.domain.Users;

public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(Users user);
    void upgradeLevel(Users user);
}
