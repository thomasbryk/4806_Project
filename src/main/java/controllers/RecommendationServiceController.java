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
    private static int recommendationCustomerRange = 3;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RecommendationService recommendationService;


    /**
     * Retrieve BookstoreOwner with given ID
     * @param id ID of BookstoreOwner to be retrieved
     * @return BookstoreOwner found in repository from given ID
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

        Iterable<Customer> cs = customerRepository.findAll();
        ArrayList<Customer> customers = new ArrayList<Customer>();

        for (Customer c : cs){
            customers.add(c);
        }


        recommendedBooks.addAll(recommendationService.getRecommendations(customer, customers, recommendationCustomerRange));
        Iterable<Book> availableBooks = bookRepository.findByAvailable(true);

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
