package tests;

import manager.ContactHelper;
import manager.GroupHelper;
import model.ContactData;
import model.GroupData;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactAddInGroupTests extends TestBase {

    @Test
    public void canAddContactInGroupTest() {

        GroupData group;
        ContactData contact;

        if (app.hbm().getGroupCount() == 0) {
            group = app.groups().createGroup(new GroupData("", "group name", "group header", "group footer"));
            if (app.hbm().getContactCount() == 0) {
                contact = app.contacts().createContact(new ContactData("", "firstname", "lastname", "phone", "email", "src/test/resources/images/image2.png", "", "", "", "", "", ""));
            } else {
                contact = app.hbm().getContactList().get(0);
            }
        } else if (app.hbm().searchGroupForAddContact() == null) {
            group = app.hbm().getGroupList().get(0);
            contact = app.contacts().createContact(new ContactData("", "firstname", "lastname", "phone", "email", "src/test/resources/images/image2.png", "", "", "", "", "", ""));
        } else {
            group = app.hbm().searchGroupForAddContact();
            var contacts = app.hbm().deleteContactsInGroup(group);
            contact = contacts.get(0);
        }

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
