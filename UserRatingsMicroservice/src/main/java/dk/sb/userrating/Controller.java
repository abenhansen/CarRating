package dk.sb.userrating;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ExecutableInsertOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Controller {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RatingRepository ratingRepository;

    private CarSearchClient carClient = null;

    public Controller(CarSearchClient carClient)
    {
        this.carClient = carClient;
    }

    @GetMapping("/ratings/cars/{id}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    @HystrixCommand(fallbackMethod = "carFallback") // in case of failure
    public Car ratings(@PathVariable Long id)
    {
        List<Car> cars = carClient.readCars()
                .getContent()
                .stream()
                .filter(car -> car.getId() == id)
                .collect(Collectors.toList());

        Car car = cars.get(0);
        car.setRatings(ratingRepository.findAllByCar(id));
        return car;
    }

    @GetMapping("/ratings/users/{name}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    @HystrixCommand(fallbackMethod = "userFallback") // in case of failure
    public Collection<Rating> ratings(@PathVariable String name)
    {
        User user = userRepository.findByName(name);
        Collection<Rating> ratings = ratingRepository.findAllByUser(user);
        return ratings;
    }

    private Car carFallback(@PathVariable Long id)
    {
        return null;
    }

    private Collection<Rating> userFallback(@PathVariable String name)
    {
        return null;
    }

}
