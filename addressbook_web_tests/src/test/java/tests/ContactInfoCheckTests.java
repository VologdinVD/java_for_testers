package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactInfoCheckTests extends TestBase {

    @Test
    public void testCheckContactsInfo() {
        var contacts = app.hbm().getContactList();
        if (contacts.size() == 0) {
            app.contacts().createContact(new ContactData(
                    "",
                    "firstname",
                    "lastname",
                    "phone",
                    "email",
                    "src/test/resources/images/image2.png",
                    "homePhone",
                    "workPhone",
                    "secondaryPhone",
                    "email2",
                    "email3",
                    "address"
            ));
        }
        var contact = app.hbm().getContactList().get(0);
        var expectedInfo = app.hbm().getListInfoOfContact(contact);
        var info = app.contacts().getInfo(contact);
        Assertions.assertEquals(expectedInfo, info);
    }
}

