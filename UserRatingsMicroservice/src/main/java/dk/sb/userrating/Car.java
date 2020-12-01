package dk.sb.userrating;

import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class Car
{
        //@Id
        //@GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @NonNull private String brand;
        @NonNull private int year;
        @NonNull private long km;
        private Collection<Rating> ratings;

        public void addRating(Rating rating) {
                if (ratings == null) {
                        ratings = new ArrayList<>();
                }
                ratings.add(rating);
        }
}
