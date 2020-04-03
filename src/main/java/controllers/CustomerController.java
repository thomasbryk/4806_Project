package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import models.Book;
import models.Customer;
import models.CustomerSpec;
import models.Sale;
import models.ShoppingCart;
import repositories.CustomerRepository;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    
    
    /**
     * Returns a list of Customers, given filters as parameters
     * @param spec see class
     * @return List of filtered Customers
     */
    @GetMapping()
    public Iterable<Customer> getCustomers(CustomerSpec spec){
        return customerRepository.findAll(spec);
    }

    /**
     * Returns a customer given the ID
     * @param id ID of customer to find
     * @return Customer with ID
     */
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable long id){
        return customerRepository.findById(id);
    }

/**
     * Creates a Customer given a JSON representation of the customer. Ex: {"name":"customer"}
     * @param customer customer to create
     * @return Created customer with an ID
     */
    @PostMapping()
    public Customer createCustomer(@RequestBody Customer customer){
        Customer b = customerRepository.save(customer);
        return b;
    }

    /**
     * Returns the shopping cart of a given customer
     * @param id ID of customer
     * @return Shopping cart of customer
     */
    @GetMapping("/{id}/shoppingcart")
    public ShoppingCart getCustomerShoppingCart(@PathVariable long id){
        Customer b = customerRepository.findById(id);
        return b.getShoppingCart();
    }

    /**
     * Returns the sales made by a given customer
     * @param id ID of the customer
     * @return List of sales of the customer
     */
    @GetMapping("/{id}/sales")
    public Iterable<Sale> getSalesByCustomer(@PathVariable long id){
        Customer b = customerRepository.findById(id);
        return b.getSales();
    }

    /**
     * Checkout a customer given their ID
     * @param id ID of customer to checkout
     * @return Sale made by checkout
     */
    @PostMapping("/{id}/checkout")
    public Sale checkoutCustomer(@PathVariable long id){
        Customer b = customerRepository.findById(id);
        ShoppingCart sc = b.getShoppingCart();
        Sale s = sc.checkout();
        b.addSale(s);
        customerRepository.save(b);
        return s;
    }

    /**
     * Appends a book to the customer's shopping cart
     * @param id ID of customer to add book to
     * @param book Book object to add to cart
     * @return Shopping cart with added book
     */
    @PutMapping("/{id}/shoppingcart")
    public ShoppingCart addBookToShoppingCart(@PathVariable long id, @RequestBody Book book){
        Customer b = customerRepository.findById(id);
        ShoppingCart sc = b.getShoppingCart();
        sc.addBook(book);
        book.addShoppingCart(sc);
        customerRepository.save(b);
        return sc;
    }

    /**
     * Appends a book to the customer's shopping cart
     * @param id ID of customer to add book to
     * @param book Book object to add to cart
     * @return Shopping cart with added book
     */
    @DeleteMapping("/{id}/shoppingcart")
    public ShoppingCart removeBookFromShoppingCart(@PathVariable long id, @RequestBody Book book){
        Customer b = customerRepository.findById(id);
        ShoppingCart sc = b.getShoppingCart();
        sc.removeBook(book);
        b = customerRepository.save(b);
        
        return b.getShoppingCart();
    }
}


