package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class ContactRemovalTests extends TestBase {

    @Test
    public void removeContactTest() {
        if (app.hbm().getContactCount() == 0) {
            app.hbm().createContact(new ContactData("", "first name", "last name", "phone", "email", ""));
        }
        var oldContacts = app.hbm().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        app.contacts().removeContact(oldContacts.get(index));
        var newContacts = app.hbm().getContactList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.remove(index);
        Assertions.assertEquals(newContacts, expectedList);
    }

    @Test
    public void removeContactFromGroupTest() {
        if (app.hbm().getGroupCount() == 0) {
            app.hbm().createGroup(new GroupData("", "group name", "group header", "group footer"));
        } else if (app.jdbc().getContactInGroupCount() == 0) {
            var group = app.hbm().getGroupList().get(0);
            app.contacts().createContactInGroup(new ContactData("", "first name", "last name", "phone", "email", "src/test/resources/images/image2.png"), group);
        }
        var group = app.hbm().getGroupList().get(0);

        var oldRelated = app.hbm().getContactsInGroup(group);
        var rnd = new Random();
        var index = rnd.nextInt(oldRelated.size());
        app.contacts().removeContactFromGroup(oldRelated.get(index), group);
        var newRelated = app.hbm().getContactsInGroup(group);
        var expectedList = new ArrayList<>(oldRelated);
        expectedList.remove(index);
        Assertions.assertEquals(newRelated, expectedList);
    }
}
