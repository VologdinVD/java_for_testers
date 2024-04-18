package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() {
        var result = new ArrayList<ContactData>();
        for (int i = 0; i < 5; i++) {
            result.add(new ContactData()
                    .withFirstName(randomString(i))
                    .withLastName(randomString(i))
                    .withMobilePhone(randomString(i))
                    .withEmail(randomString(i)));
        }
        return result;
    }

    @ParameterizedTest
    @MethodSource("contactProvider")
    public void createContactTest(ContactData contact) {
        var oldContacts = app.contacts().getList();
        app.contacts().createContact(contact);
        var newContacts = app.contacts().getList();
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);

        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(newContacts.get(newContacts.size() - 1).id()).withFirstName("").withLastName("").withMobilePhone("").withEmail(""));
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }
}
