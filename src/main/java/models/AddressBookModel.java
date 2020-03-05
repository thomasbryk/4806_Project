package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.CascadeType.ALL;

@Entity
public class AddressBookModel {
    private Long id;
    private List<BuddyInfoModel> buddies;

    public AddressBookModel(){
        this.buddies = new ArrayList<BuddyInfoModel>();
    }

    @Id
    @GeneratedValue
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    public void addBuddy(BuddyInfoModel buddyInfoModel){
        this.buddies.add(buddyInfoModel);
        buddyInfoModel.setAddressBookModel(this);
    }

    public BuddyInfoModel addBuddy(String name, String phoneNumber){
        BuddyInfoModel buddyInfoModel = new BuddyInfoModel(name, phoneNumber);
        this.buddies.add(buddyInfoModel);
        buddyInfoModel.setAddressBookModel(this);
        return buddyInfoModel;
    }

    public void removeBuddy(BuddyInfoModel buddyInfoModel){
        boolean buddyFound = false;
        BuddyInfoModel foundBuddy = null;
        for (BuddyInfoModel buddy : this.buddies){
            if (buddy.equals(buddyInfoModel)){
                buddyFound = true;
                foundBuddy = buddy;
            }
        }
        if (buddyFound){
            this.buddies.remove(foundBuddy);
            foundBuddy.removeAddressBookModel();
        }
    }

    @OneToMany(fetch = FetchType.EAGER, cascade=ALL, mappedBy = "addressBookModel")
    public List<BuddyInfoModel> getBuddies(){
        return buddies;
    }
    public void setBuddies(List<BuddyInfoModel> buddyInfoModelList){ this.buddies = buddyInfoModelList; }

    public boolean containsBuddy(BuddyInfoModel buddy){
        boolean buddyFound = false;
        for (BuddyInfoModel b : this.buddies){
            if(b.equals(buddy)){
                buddyFound = true;
                break;
            }
        }
        return buddyFound;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (BuddyInfoModel buddyInfoModel : this.buddies){
            sb.append(buddyInfoModel.toString());
        }
        return sb.toString();
    }

    public static void main (String args[]){
        AddressBookModel addressBookModel = new AddressBookModel();

        addressBookModel.addBuddy(new BuddyInfoModel("Kaj", "555-1234"));
        addressBookModel.addBuddy(new BuddyInfoModel("Thomas", "555-5678"));

        System.out.println(addressBookModel.toString());
    }
}
