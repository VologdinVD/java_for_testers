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

    public ContactRecord(){
    }

    public ContactRecord(int id, String firstName, String lastName, String mobilePhone, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePhone = mobilePhone;
        this.email = email;
    }
}
