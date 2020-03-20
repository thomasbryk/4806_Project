package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import models.BookstoreOwner;
import models.BookstoreUser;
import models.Customer;
import repositories.BookstoreOwnerRepository;
import repositories.CustomerRepository;

@Service
public class UserServiceDetails implements UserDetailsService {
    
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookstoreOwnerRepository bookstoreOwnerRepository;

    @Autowired
    public UserServiceDetails(CustomerRepository customerRepository, BookstoreOwnerRepository bookstoreOwnerRepository) {
        // this instantiates a couple users to start out with
        Customer c = new Customer("user1","pass1","Customer 1", "somewhere", "e@ma.il", "613-613-6136");
        customerRepository.save(c);

        BookstoreOwner b = new BookstoreOwner("user2", "pass2", "Bookstore owner 1");
        bookstoreOwnerRepository.save(b);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Got username: "+username);
        Customer c = customerRepository.findByUsername(username);
        if(c != null){
            return toUserDetails(c);
        }

        BookstoreOwner b = bookstoreOwnerRepository.findByUsername(username);
        if(b != null){
            return toUserDetails(b);
        }

        throw new UsernameNotFoundException("Username not found for either customer or bookstore");

    }

    private UserDetails toUserDetails(BookstoreUser user) {
        return User.withUsername(user.getUsername())
                   .password(user.getPassword())
                   .roles(user.getRole()).build();
    }

}