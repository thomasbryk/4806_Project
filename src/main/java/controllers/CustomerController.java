package controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    @GetMapping()
    public Iterable<Customer> getCustomers(CustomerSpec spec){
        return customerRepository.findAll(spec);
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable long id){
        return customerRepository.findById(id);
    }

    @PostMapping()
    public Customer createCustomer(@RequestBody Customer Customer){
        Customer b = customerRepository.save(Customer);
        return b;
    }

    @GetMapping("/{id}/shoppingCart")
    public ShoppingCart getBookstoresByCustomer(@PathVariable long id){
        Customer b = customerRepository.findById(id);
        return b.getShoppingCart();
    }

    @GetMapping("/{id}/sales")
    public Iterable<Sale> getSalesByCustomer(@PathVariable long id){
        Customer b = customerRepository.findById(id);
        return b.getSales();
    }

    @GetMapping("/{id}/checkout")
    public Sale checkoutCustomer(@PathVariable long id){
        Customer b = customerRepository.findById(id);
        ShoppingCart sc = b.getShoppingCart();
        Sale s = sc.checkout();
        b.addSale(s);
        customerRepository.save(b);
        return s;
    }


    @PutMapping("/{id}/shoppingCart")
    public ShoppingCart addBookstoreToOwner(@PathVariable long id, @RequestBody Book book){
        Customer b = customerRepository.findById(id);
        ShoppingCart sc = b.getShoppingCart();
        sc.addBook(book);
        customerRepository.save(b);
        return sc;
    }
}


