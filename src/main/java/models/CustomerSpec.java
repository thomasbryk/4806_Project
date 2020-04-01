package models;

import org.springframework.data.jpa.domain.Specification;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@And({
    @Spec(path="name",params="name",spec=Equal.class),
    @Spec(path="address",params="address",spec=Equal.class),
    @Spec(path="email",params="email",spec=Equal.class),
    @Spec(path="phoneNumber",params="phoneNumber",spec=Equal.class)
})
public interface CustomerSpec extends Specification<Customer>{
}