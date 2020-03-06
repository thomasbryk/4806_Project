package repositories;

import models.Customer;
import models.Sale;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "sales", path = "sales")
public interface SaleRepository extends PagingAndSortingRepository<models.Sale, Long> {
    Sale findById(long id);
    Iterable<Sale> findByCustomer(Customer customer);
}