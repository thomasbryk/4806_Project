package security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import models.BookstoreOwner;
import models.Customer;
import repositories.BookstoreOwnerRepository;
import repositories.CustomerRepository;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
 
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookstoreOwnerRepository bookstoreOwnerRepository;
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
 
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String username = authentication.getName();
        
        if (roles.contains("ROLE_ADMIN")) {
            BookstoreOwner o = bookstoreOwnerRepository.findByUsername(username);
            httpServletResponse.sendRedirect("/viewBookstoreOwner?bookstoreOwnerId="+o.getId());
        } else {
            Customer c = customerRepository.findByUsername(username);
            httpServletResponse.sendRedirect("/viewCustomer?customerId="+c.getId());
        }
    }
}
 