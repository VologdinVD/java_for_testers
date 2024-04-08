package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactRemovalTests extends TestBase {

    @Test
    public void removeContactTest() {
        if (!app.contacts().isContactPresent()) {
            app.contacts().createContact(new ContactData("first name", "last name", "mobile phone", "email"));
        }
        app.contacts().removeContact();
    }
}
