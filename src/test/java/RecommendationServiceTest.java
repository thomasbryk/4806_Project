import models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import repositories.BookRepository;
import repositories.CustomerRepository;
import services.RecommendationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {application.WebLauncher.class})
public class RecommendationServiceTest {
    private Customer customer1, customer2, customer3, customer4, customer5;
    private Book book1, book2, book3, book4, book5, book6, book7;

    @Autowired
    private RecommendationService recommendationService;

    @Before
    public void setUp(){
        this.customer1 = new Customer("Customer1", "cust1-address", "cust1-email", "cust1-phoneNumber");
        this.customer2 = new Customer("Customer2", "cust2-address", "cust2-email", "cust2-phoneNumber");
        this.customer3 = new Customer("Customer3", "cust3-address", "cust3-email", "cust3-phoneNumber");
        this.customer4 = new Customer("Customer4", "cust4-address", "cust4-email", "cust4-phoneNumber");
        this.customer5 = new Customer("Customer5", "cust5-address", "cust5-email", "cust5-phoneNumber");

        this.book1 = new Book("Book 1", "book1-isbn", "book1-picture", "book1-description", "book1-author", "book1-publisher");
        this.book2 = new Book("Book 2", "book2-isbn", "book2-picture", "book2-description", "book2-author", "book2-publisher");
        this.book3 = new Book("Book 3", "book3-isbn", "book3-picture", "book3-description", "book3-author", "book3-publisher");
        this.book4 = new Book("Book 4", "book4-isbn", "book4-picture", "book4-description", "book4-author", "book4-publisher");
        this.book5 = new Book("Book 5", "book5-isbn", "book5-picture", "book5-description", "book5-author", "book5-publisher");
        this.book6 = new Book("Book 6", "book6-isbn", "book6-picture", "book6-description", "book6-author", "book6-publisher");
        this.book7 = new Book("Book 7", "book7-isbn", "book7-picture", "book7-description", "book7-author", "book7-publisher");

        ShoppingCart shoppingCart1 = this.customer1.getShoppingCart();
        shoppingCart1.addBook(this.book1);
        shoppingCart1.addBook(this.book2);
        shoppingCart1.checkout();

        ShoppingCart shoppingCart2 = this.customer2.getShoppingCart();
        shoppingCart2.addBook(this.book1);
        shoppingCart2.addBook(this.book2);
        shoppingCart2.addBook(this.book3);
        shoppingCart2.checkout();

        ShoppingCart shoppingCart3 = this.customer3.getShoppingCart();
        shoppingCart3.addBook(this.book1);
        shoppingCart3.addBook(this.book5);
        shoppingCart3.checkout();

        ShoppingCart shoppingCart4 = this.customer4.getShoppingCart();
        shoppingCart4.addBook(this.book2);
        shoppingCart4.addBook(this.book5);
        shoppingCart4.addBook(this.book6);
        shoppingCart4.checkout();

        ShoppingCart shoppingCart5 = this.customer5.getShoppingCart();
        shoppingCart5.addBook(this.book3);
        shoppingCart5.addBook(this.book4);
        shoppingCart5.addBook(this.book7);
        shoppingCart5.checkout();
    }

    /**
     * Test the getDistance() method in RecommendationService.
     *
     * Expected condition: The distances as noted in the below assert statements are returned.
     */
    @Test
    public void TestGetDistance(){
        assert(this.recommendationService.getDistance(this.customer1, this.customer2) == 33.33);
        assert(this.recommendationService.getDistance(this.customer1, this.customer3) == 66.67);
        assert(this.recommendationService.getDistance(this.customer1, this.customer4) == 75.00);
        assert(this.recommendationService.getDistance(this.customer1, this.customer5) == 100);
    }

    /**
     * Test the getFurthestCustomer() method in RecommendationService. This method traverses a map, where the key is a
     * customer and the value is the distance, it is expected to return the customer with the largest distance.
     *
     * Expected condition: Customer4 is returned as it has the furthest distance.
     */
    @Test
    public void TestGetFurthestCustomer(){
        HashMap<Customer, Double> distanceBySimiliarCustomerMap = new HashMap<Customer, Double>();

        distanceBySimiliarCustomerMap.put(this.customer2, 33.33);
        distanceBySimiliarCustomerMap.put(this.customer3, 66.67);
        distanceBySimiliarCustomerMap.put(this.customer4, 75.00);

        assert(this.recommendationService.getFurthestCustomer(distanceBySimiliarCustomerMap).equals(this.customer4));
    }

    /**
     * Test the getFurthestCustomer() method in RecommendationService. This method traverses a map, where the key is a
     * customer and the value is the distance, it is expected to return the customer with the largest distance.
     *
     * Expected condition: Customer4 is returned as it has the furthest distance.
     */
    @Test
    public void TestGetRecommendations(){
        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(this.customer2);
        customers.add(this.customer3);
        customers.add(this.customer4);
        customers.add(this.customer5);

        HashSet<Book> expectedRecommendations = new HashSet<Book>();
        expectedRecommendations.add(this.book3);
        expectedRecommendations.add(this.book5);
        expectedRecommendations.add(this.book6);

        assert(this.recommendationService.getRecommendations(this.customer1, customers, 3).equals(expectedRecommendations));
    }
}
