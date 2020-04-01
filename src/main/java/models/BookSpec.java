package models;

import org.springframework.data.jpa.domain.Specification;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@And({
    @Spec(path="name",params="name",spec=Equal.class),
    @Spec(path="isbn",params="isbn",spec=Equal.class),
    @Spec(path="picture",params="picture",spec=Equal.class),
    @Spec(path="description",params="description",spec=Equal.class),
    @Spec(path="publisher",params="publisher",spec=Equal.class)
})
public interface BookSpec extends Specification<Book>{
}