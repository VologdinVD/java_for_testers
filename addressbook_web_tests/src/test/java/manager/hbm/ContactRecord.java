package manager.hbm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addressbook")
public class ContactRecord {

    @Id
    public int id;

    @Column(name = "firstname")
    public String firstName;

    @Column(name = "lastname")
    public String lastName;

    @Column(name = "mobile")
    public String mobilePhone;

    public String email;

    @Column(name = "home")
    public String homePhone;

    @Column(name = "work")
    public String workPhone;

    @Column(name = "phone2")
    public String secondaryPhone;

    public String email2;

    public String email3;

    public String address = "address";

    public String middlename = "middlename";

    public String nickname = "nickname";

    public String company = "company";

    public String title = "title";

    public String fax = "fax";

    public String homepage = "homepage";

    public ContactRecord(){
    }

    public ContactRecord(int id, String firstName, String lastName, String mobilePhone, String homePhone, String workPhone, String email, String email2, String email3, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.email = email;
        this.email2 = email2;
        this.email3 = email3;
        this.address = address;
    }
}
