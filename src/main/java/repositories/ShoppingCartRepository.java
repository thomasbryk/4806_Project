package repositories;

import models.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "shoppingcarts", path = "shoppingcarts")
public interface ShoppingCartRepository extends PagingAndSortingRepository<ShoppingCart, Long> {
    ShoppingCart findById(long id);
    ShoppingCart findByCustomer(Customer customer);
}