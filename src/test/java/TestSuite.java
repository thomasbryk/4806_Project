import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BookstoreOwnerTest.class,
        BookstoreTest.class,
        BookTest.class,
        ShoppingCartTest.class
})

public class TestSuite {
}  	