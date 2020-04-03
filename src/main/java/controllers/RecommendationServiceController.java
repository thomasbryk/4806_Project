package controllers;

import models.Book;
import models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import repositories.BookRepository;
import repositories.CustomerRepository;
import services.RecommendationService;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Controller with endpoints used to generate book recommendations
 */
@RestController
public class RecommendationServiceController {
    private static int recommendationCustomerRange = 3;             //Determines how many customers are used to generate recommendations.

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RecommendationService recommendationService;


    /**
     * Gets book recommendations for customer. Book recommendations are based on purchases from other customers that h
     * have a similiar purchase history to the current customer. Recommendations are only shown if another copy
     * of the same book is available for sale.
     *
     * @param id ID of Customer to be retrieved
     * @return list of book recommendations
     */
    @GetMapping("/api/getBookRecommendations/{id}")
    public ArrayList<Book> getBookRecommendations(@PathVariable long id) {
        Customer customer = customerRepository.findById(id);
        HashSet<Book> recommendedBooks = new HashSet<Book>();
        ArrayList<Book> recommendedAndAvailableBooks = new ArrayList<Book>();

        //Do not use recommendationService if the customer has not purchased any books yet
        if (customer.getPurchasedBooks().isEmpty()){
            return recommendedAndAvailableBooks;
        }

        // Get all customers, and convert to ArrayList
        Iterable<Customer> cs = customerRepository.findAll();
        ArrayList<Customer> customers = new ArrayList<Customer>();
        for (Customer c : cs){
            customers.add(c);
        }

        //Get all recommendations for Customer from RecommendationService
        recommendedBooks.addAll(recommendationService.getRecommendations(customer, customers, recommendationCustomerRange));
        Iterable<Book> availableBooks = bookRepository.findByAvailable(true);

        //For each recommendation, add it to recommended books to display ONLY if there is another copy available for purchase.
        for (Book recommendedBook : recommendedBooks){
            for (Book availableBook : availableBooks){
                if (recommendedBook.equals(availableBook)){
                    recommendedAndAvailableBooks.add(availableBook);
                    break;
                }
            }
        }

        return recommendedAndAvailableBooks;
    }
}
