package dk.sb.userrating;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data
public class Rating {
    @Id
    public String id;
    public int rating;
    public User user;
    public int car;

    public Rating(int rating, User user, int car) {
        this.rating = rating;
        this.user = user;
        this.car = car;
    }
}
