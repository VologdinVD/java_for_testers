package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;


public class ContactCreationTests extends TestBase {

    @Test
    public void createContactTest() {
      app.contacts().createContact(new ContactData("first name", "last name", "phone", "email@mail.ru"));
    }
}
