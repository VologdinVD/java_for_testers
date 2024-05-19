package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

    public RegistrationHelper(ApplicationManager manager) {
        super(manager);
    }

    public void signUpForNewAccount(String username, String email) {
        signUp();
        fillFormOfSignUp(username, email);
        submit();
        proceed();
    }

    private void submit() {
        manager.driver().findElement(By.xpath("//input[@value='Signup']")).click();
    }

    private void fillFormOfSignUp(String username, String email) {
        type(By.name("username"), username);
        type(By.name("email"), email);
    }

    private void signUp() {
        manager.driver().findElement(By.linkText("Signup for a new account")).click();
    }

    private void proceed() {
        manager.driver().findElement(By.linkText("Proceed")).click();
    }

    public void verifyUser(String url, String realname, String password, String confirmPassword) {
        manager.driver().get(url);
        type(By.id("realname"), realname);
        type(By.id("password"), password);
        type(By.id("password-confirm"), confirmPassword);
        manager.driver().findElement(By.xpath("//*[@id=\"account-update-form\"]/fieldset/span/button")).click();
    }
}
