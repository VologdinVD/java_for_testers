package manager;

import model.ContactData;
import org.openqa.selenium.By;

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

    public void removeContact() {
        selectContact();
        removeSelectedContact();
        //closeDialogWindow();
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
    }

    private void selectContact() {
        click(By.name("selected[]"));
    }

    private void closeDialogWindow() {
        manager.driver.switchTo().alert().accept();
    }

}