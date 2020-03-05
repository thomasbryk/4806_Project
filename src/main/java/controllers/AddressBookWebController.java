package controllers;

import models.AddressBookModel;
import models.BuddyInfoModel;
import repositories.AddressBookModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddressBookWebController {

    @Autowired
    private AddressBookModelRepository addressBookModelRepository;

    @GetMapping("/addressBook")
    public String viewAddressBook(Model model) {
        Iterable <AddressBookModel> addressBooks = addressBookModelRepository.findAll();
        model.addAttribute("addressBooks", addressBooks);
        return "index";
    }

    @PostMapping("/newAddressBook")
    public String newAddressBook(Model model) {
        AddressBookModel addressBook = new AddressBookModel();
        addressBookModelRepository.save(addressBook);
        return "redirect:/addressBook";
    }

    @PostMapping("/addBuddy")
    public String addBuddy(@RequestParam(value="id") long id, @RequestParam(value="name") String name, @RequestParam(value="phonenumber") String phonenumber, Model model) {
        AddressBookModel addressBook = addressBookModelRepository.findById(id);
        addressBook.addBuddy(name, phonenumber);
        addressBookModelRepository.save(addressBook);
        model.addAttribute("addressBook", addressBook);
        return "viewAddressBook";
    }

    @PostMapping("/removeBuddy")
    public String removeBuddy(@RequestParam(value="id") long id, @RequestParam(value="name") String name, @RequestParam(value="phonenumber") String phonenumber, Model model ) {
        AddressBookModel addressBook = addressBookModelRepository.findById(id);
        addressBook.removeBuddy(new BuddyInfoModel(name, phonenumber));
        addressBookModelRepository.save(addressBook);
        model.addAttribute("addressBook", addressBook);
        return "viewAddressBook";
    }

    @GetMapping("/viewAddressBook")
    public String viewAddressBook(@RequestParam(value="id") long id, Model model) {
        AddressBookModel addressBook = addressBookModelRepository.findById(id);
        model.addAttribute("addressBook", addressBook);
        return "viewAddressBook";
    }
}
