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

    public UserServiceDetails() {

    }

    /**
     * Loads a user's credentials given a username. First sees if it is a customer, then checks if it is a bookstore Owner
     */
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