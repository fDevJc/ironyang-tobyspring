package ironyang.tobyspring.user.domain;

import lombok.Data;

@Data
public class Users {
    private Long id;
    private String name;
    private String password;
    private Level level;
    private int login;
    private int recommend;

    public Users(Long id, String name, String password, Level level, int login, int recommend) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }

    public Users() {

    }

    public void upgradeLevel() {
        Level nextLevel = this.level.getNext();
        if (nextLevel == null) {
            throw new IllegalStateException(this.level + "은 업그레이드가 불가능합니다.");
        } else {
            this.level = nextLevel;
        }
    }
}
