package ironyang.tobyspring.user.service.levelupgradepolicy;

import ironyang.tobyspring.user.domain.Level;
import ironyang.tobyspring.user.domain.Users;

public class SimpleUserLevelUpgradePolicy implements UserLevelUpgradePolicy{
    public static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    @Override
    public boolean canUpgradeLevel(Users user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC:
                return (user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
            case SILVER:
                return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("unknown level" + currentLevel);
        }
    }

    @Override
    public void upgradeLevel(Users user){
        user.upgradeLevel();
    }
}
