import models.AddressBookModel;
import models.BuddyInfoModel;
import org.junit.Before;
import org.junit.Test;

public class AddressBookModelTest {
    private AddressBookModel addressBookModel = null;
    private BuddyInfoModel buddy = null;
    private String buddyName = "Homer";
    private String buddyPhoneNumber = "555-1234";

    @Before
    public void setUp(){
        addressBookModel = new AddressBookModel();
        buddy = new BuddyInfoModel(buddyName, buddyPhoneNumber);
        addressBookModel.addBuddy(buddy);
    }

    @Test
    public void addBuddy() {
        assert(addressBookModel.getBuddies().contains(buddy));
    }

    @Test
    public void removeBuddy() {
        addressBookModel.removeBuddy(buddy);
        assert(!addressBookModel.getBuddies().contains(buddy));
    }

    @Test
    public void getBuddies() {
        assert(addressBookModel.getBuddies().contains(buddy));
    }

    @Test
    public void testToString() {
        assert(addressBookModel.getBuddies().get(0).toString().equals("name: " + buddyName + " \n" + "phone number: " + buddyPhoneNumber + "\n"));
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
        BuddyInfoModel neverBeforeSeenBuddy = new BuddyInfoModel("NewNeverBeforeSeenBuddy", "9998765432");
        addressBookModel.addBuddy(neverBeforeSeenBuddy);
        em.persist(addressBookModel);
        tx.commit();

        Query q = em.createQuery("SELECT a FROM AddressBookModel a");// Querying the contents of the database using JPQL query

        @SuppressWarnings("unchecked")
        List<AddressBookModel> results = q.getResultList();
        AddressBookModel persistedAddressBookModel = results.get(0);

        // Closing connection
        em.close();
        emf.close();

        System.out.println(persistedAddressBookModel.toString());
        assert (persistedAddressBookModel.containsBuddy(buddy));
    }*/
}