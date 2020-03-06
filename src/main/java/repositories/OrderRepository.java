package repositories;

import models.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    Order findById(long id);
}