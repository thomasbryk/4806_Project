package repositories;

import models.Bookstore;
import models.BookstoreOwner;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "bookstores", path = "bookstores")
public interface BookstoreRepository extends PagingAndSortingRepository<Bookstore, Long>, JpaSpecificationExecutor<Bookstore> {
    Bookstore findById(long id);
    List<Bookstore> findByName(String name);
   /* todo: uncomment when bookstoreOwner created
   List<Bookstore> findByBookstoreOwner(BookstoreOwner bookstoreOwner);
    */
}