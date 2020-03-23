package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import models.Customer;

@Repository
@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>, JpaSpecificationExecutor<Customer>{
    Customer findById(long id);
    List<Customer> findByName(String name);
    List<Customer> findByAddress(String address);
    List<Customer> findByEmail(String email);
    List<Customer> findByPhoneNumber(String phoneNumber);
    Customer findByUsername(String username);
}