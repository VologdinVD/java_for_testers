package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactInfoCheckTests extends TestBase {

    @Test
    public void testCheckContactsPhones() {
        var contacts = app.hbm().getContactList();
        if (contacts.size() == 0) {
            app.hbm().createContact(new ContactData(
                    "",
                    "firstname",
                    "lastname",
                    "phone",
                    "email",
                    "",
                    "homePhone",
                    "workPhone",
                    "secondaryPhone",
                    "emailSecond",
                    "emailThird",
                    "address"
            ));
        } else {
            var contact = contacts.get(0);
            var expectedInfo = Stream.of(
                            contact.address(),
                            contact.email(),
                            contact.emailSecond(),
                            contact.emailThird(),
                            contact.homePhone(),
                            contact.mobilePhone(),
                            contact.workPhone(),
                            contact.secondaryPhone())
                    .filter(s -> s != null && ! "".equals(s))
                    .collect(Collectors.joining("\n"));
            var info = app.contacts().getInfo(contact);
            Assertions.assertEquals(expectedInfo, info);
        }
    }
 }

