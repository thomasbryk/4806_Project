package repositories;

import models.ShoppingCart;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "shoppingcarts", path = "shoppingcarts")
public interface ShoppingCartRepository extends PagingAndSortingRepository<ShoppingCart, Integer> {

}