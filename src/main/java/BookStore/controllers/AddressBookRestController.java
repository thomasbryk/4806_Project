package BookStore.controllers;

import BookStore.models.AddressBookModel;
import BookStore.models.BuddyInfoModel;
import BookStore.repositories.AddressBookModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
public class AddressBookRestController {

    @Autowired
    private AddressBookModelRepository addressBookModelRepository;

    @PostMapping("/api/newAddressBook")
    public AddressBookModel addressBook() {
        AddressBookModel addressBook = new AddressBookModel();
        addressBookModelRepository.save(addressBook);
        return addressBook;
    }

    @GetMapping("/api/getAddressBooks")
    public Iterable <AddressBookModel> getAddressBooks() {
        Iterable <AddressBookModel> addressBooks = addressBookModelRepository.findAll();
        return addressBooks;
    }

    @GetMapping("/api/getAddressBook")
    public AddressBookModel addressBook(@RequestParam(value="id") long id) {
        AddressBookModel addressBook = addressBookModelRepository.findById(id);
        return addressBook;
    }

    @PostMapping("/api/addBuddy")
    public AddressBookModel addBuddy(@RequestParam(value="id") long id, @RequestParam(value="name") String name, @RequestParam(value="phonenumber") String phonenumber ) {
        AddressBookModel addressBook = addressBookModelRepository.findById(id);
        addressBook.addBuddy(name, phonenumber);
        addressBookModelRepository.save(addressBook);
        return addressBook;
    }

    @PostMapping("/api/removeBuddy")
    public AddressBookModel removeBuddy(@RequestParam(value="id") long id, @RequestParam(value="name") String name, @RequestParam(value="phonenumber") String phonenumber ) {
        AddressBookModel addressBook = addressBookModelRepository.findById(id);
        addressBook.removeBuddy(new BuddyInfoModel(name, phonenumber));
        addressBookModelRepository.save(addressBook);
        return addressBook;
    }
}