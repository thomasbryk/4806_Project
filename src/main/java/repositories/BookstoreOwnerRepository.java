package repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import models.BookstoreOwner;

@RepositoryRestResource(collectionResourceRel = "bookstoreowners", path = "bookstoreowners")
public interface BookstoreOwnerRepository extends PagingAndSortingRepository<BookstoreOwner, Long> {
    BookstoreOwner findById(long id);
    List<BookstoreOwner> findByName(String name);
    BookstoreOwner findByUsername(String username);
}