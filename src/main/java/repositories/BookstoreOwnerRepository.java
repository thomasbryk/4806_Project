package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import models.BookstoreOwner;

@RepositoryRestResource(collectionResourceRel = "bookstoreowners", path = "bookstoreowners")
public interface BookstoreOwnerRepository extends PagingAndSortingRepository<BookstoreOwner, Long>, JpaSpecificationExecutor<BookstoreOwner>{
    BookstoreOwner findById(long id);
    List<BookstoreOwner> findByName(String name);
    BookstoreOwner findByUsername(String username);
}