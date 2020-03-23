package models;

import org.springframework.data.jpa.domain.Specification;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@Or({@Spec(path="name",params="name",spec=Equal.class)})
public interface BookstoreSpec extends Specification<Bookstore>{
}