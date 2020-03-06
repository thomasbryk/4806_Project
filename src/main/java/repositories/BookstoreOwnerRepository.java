package repositories;

import models.Book;
import models.BookstoreOwner;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "bookstoreowners", path = "bookstoreowners")
public interface BookstoreOwnerRepository extends PagingAndSortingRepository<BookstoreOwner, Long> {
    BookstoreOwner findById(long id);
    List<BookstoreOwner> findByName(String name);
}