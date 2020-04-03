import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BookstoreOwnerTest.class,
        BookstoreTest.class,
        BookTest.class,
        ShoppingCartTest.class,
        RecommendationServiceTest.class
})

public class TestSuite {
}  	