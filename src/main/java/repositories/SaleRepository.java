package repositories;

import models.Book;
import models.Bookstore;
import models.Customer;
import models.Sale;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "sales", path = "sales")
public interface SaleRepository extends PagingAndSortingRepository<models.Sale, Long>, JpaSpecificationExecutor<Sale>{
    Sale findById(long id);
    Iterable<Sale> findByCustomer(Customer customer);
}