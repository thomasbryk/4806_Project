package services;

import models.Book;
import models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.CustomerRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    /**
     * Returns the book recommendations for a 'customer' given the purchase history of other 'customers'. The recommendations
     * will be from 'range' customers. The criteria to determine which customers are used to generate the recommendations
     * is the Jaccard distance algorithm.
     *
     *
     * @param customer
     * @param customers
     * @param range
     * @return
     */
    public HashSet<Book> getRecommendations(Customer customer, ArrayList<Customer> customers, Integer range){
        HashSet<Book> bookRecommendations = new HashSet<Book>();

        //Get the customers purchased books
        ArrayList<Book> customersPurchasedBooks = customer.getPurchasedBooks();

        //Return the top 'range' closest customers
        ArrayList<Customer> closeCustomers = findClosestCustomers(customer, customers, range);

        //For each of the close customers, any books that the customer does not currently own, add it to their ecommendations
        for (Customer closeCustomer : closeCustomers){
            ArrayList<Book> closeCustomersPurchasedBooks = closeCustomer.getPurchasedBooks();
            for (Book book : closeCustomersPurchasedBooks){
                if (!customersPurchasedBooks.contains(book)){
                    bookRecommendations.add(book);
                }
            }
        }

        return bookRecommendations;
    }

    /**
     * Use the Jaccard distance algorithm to find the closest 'range' 'customers' to 'customer'
     *
     * @param customer
     * @param customers
     * @param range
     * @return
     */
    private ArrayList<Customer> findClosestCustomers(Customer customer, ArrayList<Customer> customers, int range){
        HashMap<Customer, Double> distanceBySimiliarCustomerMap = new HashMap<Customer, Double>();

        //Iterate through each customer and keep the 'range' closest
        for (Customer c : customers){
            //get distance of the current customer
            Double distance = getDistance(customer, c);
            //have 'range' closest customers been found yet? if not, then this is one of the closest customers
            if (distanceBySimiliarCustomerMap.size() < range) {
                distanceBySimiliarCustomerMap.put(c, distance);
            } else {
                //If 'range' closest customers have been found, find out if this customer is closer than the furthest customer
                Customer furthestCustomer = getFurthestCustomer(distanceBySimiliarCustomerMap);

                //If the distance of this customer is less than the furthest customer, then replace furthestCustomer with this customer
                if (distance < distanceBySimiliarCustomerMap.get(furthestCustomer)) {
                    distanceBySimiliarCustomerMap.remove(furthestCustomer);
                    distanceBySimiliarCustomerMap.put(c, distance);
                }
            }
        }

        return new ArrayList<Customer>(distanceBySimiliarCustomerMap.keySet());
    }

    /**
     * Calculate the jaccard distance between two customers. Uses the customers purchased books to determine
     * the distance. The formula is:
     *              1 - ((# purchased books in common / #unique purchased books)*100)
     *
     * This method returns a Double with two decimal points.
     * @param customer1
     * @param customer2
     * @return
     */
    public Double getDistance(Customer customer1, Customer customer2){
        Double distance = 0.0;
        ArrayList<Book> customer1Books = customer1.getPurchasedBooks();
        ArrayList<Book> customer2Books = customer2.getPurchasedBooks();
        HashSet<Book> allBooks = new HashSet<Book>();


        Set<Book> sharedBooks = customer1Books.stream()
                .distinct()
                .filter(customer2Books::contains)
                .collect(Collectors.toSet());

        allBooks.addAll(customer1Books);
        allBooks.addAll(customer2Books);

        distance = ((double)sharedBooks.size() / (double)allBooks.size()) * 100;
        return (100.00 - Math.round(distance*100.0)/100.0);
    }

    /**
     * Helper method to get the furthest customer from a map that contains the customer as the key and distance as the value.
     * @param distanceBySimiliarCustomerMap
     * @return
     */
    public Customer getFurthestCustomer(HashMap<Customer, Double> distanceBySimiliarCustomerMap){
        Customer furthestCustomer = null;

        for (Customer customer : distanceBySimiliarCustomerMap.keySet()){
            if (furthestCustomer == null){
                furthestCustomer = customer;
            } else {
                if (distanceBySimiliarCustomerMap.get(customer) > distanceBySimiliarCustomerMap.get(furthestCustomer)){
                    furthestCustomer = customer;
                }
            }
        }

        return furthestCustomer;
    }
}
