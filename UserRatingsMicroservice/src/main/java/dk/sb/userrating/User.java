package dk.sb.userrating;


import org.springframework.data.annotation.Id;

import java.util.Objects;

public class User {
    @Id
    public String id;
    public String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%s, name='%s']",
                id, name);
    }
}
