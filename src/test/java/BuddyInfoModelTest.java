import models.BuddyInfoModel;
import org.junit.Before;
import org.junit.Test;

public class BuddyInfoModelTest {
    private BuddyInfoModel buddy = null;
    private String buddyName = "Homer";
    private String buddyPhoneNumber = "555-1234";

    @Before
    public void setUp() {
        buddy = new BuddyInfoModel(buddyName, buddyPhoneNumber);
    }

    @Test
    public void getName() {
        assert(buddy.getName().equals(buddyName));
    }

    @Test
    public void setName() {
        buddy.setName("TempName");
        assert(buddy.getName().equals("TempName"));
        buddy.setName(buddyName);
    }

    @Test
    public void getPhoneNumber() {
        assert(buddy.getPhoneNumber().equals(buddyPhoneNumber));
    }

    @Test
    public void setPhoneNumber() {
        buddy.setPhoneNumber("TempNumber");
        assert(buddy.getPhoneNumber().equals("TempNumber"));
        buddy.setPhoneNumber(buddyPhoneNumber);
    }

    @Test
    public void testToString() {
        assert(buddy.toString().equals("name: " + buddyName + " \n" + "phone number: " + buddyPhoneNumber + "\n"));
    }

    //@Test
    /*public void testPersistence(){
        // Connecting to the database through EntityManagerFactory
        // connection details loaded from persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emf.createEntityManager();

        // Creating a new transaction
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(buddy);
        tx.commit();

        Query q = em.createQuery("SELECT b FROM BuddyInfoModel b");// Querying the contents of the database using JPQL query

        @SuppressWarnings("unchecked")
        List<BuddyInfoModel> results = q.getResultList();
        BuddyInfoModel persistedBuddy = results.get(0);

        // Closing connection
        em.close();
        emf.close();

        assert(buddy.equals(persistedBuddy));
    }*/
}