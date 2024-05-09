package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactAddInGroupTests extends TestBase {

    @Test
    public void canAddContactInGroupTest() {
        List<ContactData> allContacts;
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        } else {
            allContacts = app.hbm().getContactList();
            var contactsCount = app.hbm().getContactCount();
            var allGroups = app.hbm().getGroupList();
            for (var group: allGroups) {
                var contactsCountInGroup = app.hbm().getContactsInGroupCount(group);
                if (contactsCount > contactsCountInGroup) {
                    var contactsInGroup = app.hbm().getContactsInGroup(group);
                    allContacts.removeAll(contactsInGroup);
                } else {
                    app.contacts().createContact(new ContactData("", "firstname", "lastname", "phone", "email", "src/test/resources/images/image2.png", "", "", "", "", "", ""));
                    break;
                }
            }
        }
        allContacts = app.hbm().getContactList();
        var group = app.hbm().getGroupList().get(0);
        var contact = allContacts.get(allContacts.size() - 1);

        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().addContactInGroup(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newRelated.sort(compareById);

        var expectedList = new ArrayList<>(oldRelated);
        expectedList.add(contact);
        expectedList.sort(compareById);
        Assertions.assertEquals(newRelated, expectedList);
    }
}
