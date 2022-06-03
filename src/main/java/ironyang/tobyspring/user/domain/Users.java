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
}
