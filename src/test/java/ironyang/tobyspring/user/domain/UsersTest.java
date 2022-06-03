package ironyang.tobyspring.user.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UsersTest {
    private Users user;
    private Level[] levels;

    @BeforeEach
    void setUp() {
        user = new Users();
        levels = Level.values();
    }
    @Test
    void upgradeLevel() {
        for (Level level : levels) {
            if(level.getNext() == null ) continue;
            user.setLevel(level);
            user.upgradeLevel();
            assertThat(user.getLevel()).isEqualTo(level.getNext());
        }
    }

    @Test
    void upgradeLevel_with_IllegalStateException() {
        for (Level level : levels) {
            if(level.getNext() != null ) continue;
            user.setLevel(level);
            assertThatThrownBy(() -> user.upgradeLevel())
                    .isInstanceOf(IllegalStateException.class);
        }
    }
}