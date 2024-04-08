package model;

public record ContactData(String firstName, String lastName, String mobilePhone, String email) {

    public ContactData() {
        this("", "", "", "");
    }
}
