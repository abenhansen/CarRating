package dk.sb.userrating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Collection;

@EnableHystrixDashboard
@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class UserratingApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RatingRepository ratingRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserratingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        userRepository.deleteAll();
        ratingRepository.deleteAll();

        // save a couple of customers
        User user1 = userRepository.save(new User("Simon"));
        User user2 = userRepository.save(new User("Claus"));
        User user3 = userRepository.save(new User("Martin"));

        Rating rating = new Rating(5,user1,1);
        rating = ratingRepository.save(rating);

        Collection<Rating> ratings = ratingRepository.findAllByUser(user1);
        System.out.println(ratings);

        // fetch all customers
        /*System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : repository.findByLastName("Smith")) {
            System.out.println(customer);
        }*/

    }

}
