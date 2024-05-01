package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openCreationContactPage() {
        if (!manager.isElementPresent(By.name("new"))) {
            manager.driver.findElement(By.linkText("add new")).click();
        }
    }

    public boolean isContactPresent() {
        return manager.isElementPresent(By.name("home"));
    }

    public void createContact(ContactData contact) {
        openCreationContactPage();
        fillContactForm(contact);
        submitContactCreate();
        returnToHomePage();
    }

    public void createContactInGroup(ContactData contact, GroupData group) {
        openCreationContactPage();
        fillContactForm(contact);
        selectGroup(group);
        submitContactCreate();
        returnToHomePage();
    }

    private void selectGroup(GroupData group) {
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
    }

    public void removeContact(ContactData contact) {
        selectContact(contact);
        removeSelectedContact();
        //closeDialogWindow();
    }

    private void selectContact(ContactData contact) {
        click(By.cssSelector(String.format("input[value='%s']", contact.id())));
    }

    private void removeSelectedContact() {
        click(By.xpath("//input[@value=\'Delete\']"));
    }

    private void returnToHomePage() {
        manager.driver.findElement(By.linkText("home page")).click();
    }

    private void submitContactCreate() {
        click(By.name("submit"));
    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstName());
        type(By.name("lastname"), contact.lastName());
        type(By.name("mobile"), contact.mobilePhone());
        type(By.name("email"), contact.email());
        attach(By.name("photo"), contact.photo());
    }

    private void clickByIconEdit(ContactData contact) {
        click(By.cssSelector(String.format("a[href*='edit.php?id=%s']", contact.id())));
    }

    private void closeDialogWindow() {
        manager.driver.switchTo().alert().accept();
    }

    public List<ContactData> getList() {
        var contacts = new ArrayList<ContactData>();
        var trs = manager.driver.findElements(By.cssSelector("tr[name=\"entry\"]"));
        for (var tr: trs) {
            var cells = tr.findElements(By.tagName("td"));
            var firstName = cells.get(2).getText();
            var lastName = cells.get(1).getText();
            var checkbox = tr.findElement(By.name("selected[]"));
            var id = checkbox.getAttribute("value");
            contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName));
        }
        return contacts;
    }

    public void modifyContact(ContactData contactData, ContactData modifiedContact) {
        clickByIconEdit(contactData);
        fillContactForm(modifiedContact);
        submitContactModification();
        returnToHomePage();
    }

    private void submitContactModification() {
        click(By.name("update"));
    }

    public int getCount() {
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    public void removeContactFromGroup(ContactData contact, GroupData group) {
        selectGroupForContacts(group);
        selectContact(contact);
        removeSelectedContactFromGroup();
    }

    private void removeSelectedContactFromGroup() {
        manager.driver.findElement(By.name("remove")).click();
    }

    private void selectGroupForContacts(GroupData group) {
        new Select(manager.driver.findElement(By.name("group"))).selectByValue(group.id());
    }
}