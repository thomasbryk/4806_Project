//package index.js;
//
//import index.js.models.AddressBookModel;
//import index.js.repositories.AddressBookModelRepository;
//import index.js.models.BuddyInfoModel;
//import index.js.repositories.BuddyInfoModelRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
//
//public class AccessingDataJpaApplication {
//
//    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);
//
//    public static void main(String[] args) {
//        SpringApplication.run(AccessingDataJpaApplication.class, args);
//    }
//
//    @Bean
//    public CommandLineRunner demo(AddressBookModelRepository addressBookModelRepository, BuddyInfoModelRepository buddyInfoModelRepository){
//        return (args) -> {
//            AddressBookModel addressBookModel1 = new AddressBookModel();
//            addressBookModel1.addBuddy("Buddy1", "1234567890");
//            addressBookModel1.addBuddy("Buddy2", "420691234");
//
//            AddressBookModel addressBookModel2 = new AddressBookModel();
//            addressBookModel2.addBuddy("Buddy3", "1234567890");
//            addressBookModel2.addBuddy("Buddy4", "420691234");
//
//            // save a few address books
//            addressBookModelRepository.save(addressBookModel1);
//            addressBookModelRepository.save(addressBookModel2);
//
//            // fetch all customers
//            log.info("AddressBooks found with findAll():");
//            log.info("-------------------------------");
//            for (AddressBookModel addressBookModel : addressBookModelRepository.findAll()) {
//                log.info(addressBookModel.toString());
//            }
//            log.info("");
//
//            // fetch all customers
//            log.info("BuddyInfos found with findByPhoneNumber():");
//            log.info("-------------------------------");
//            for (BuddyInfoModel buddyInfoModel : buddyInfoModelRepository.findByPhoneNumber("420691234")) {
//                log.info(buddyInfoModel.toString());
//            }
//            log.info("");
//        };
//    }
//
//}
