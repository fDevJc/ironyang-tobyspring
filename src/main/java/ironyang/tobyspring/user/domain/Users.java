package ironyang.tobyspring.user.domain;

import lombok.Data;

@Data
public class Users {
    private Long id;
    private String name;
    private String password;

    public Users(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Users() {

    }
}
