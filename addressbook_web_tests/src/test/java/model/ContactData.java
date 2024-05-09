package model;

public record ContactData(
        String id,
        String firstName,
        String lastName,
        String mobilePhone,
        String email,
        String photo,
        String homePhone,
        String workPhone,
        String secondaryPhone,
        String emailSecond,
        String emailThird,
        String address
) {

    public ContactData() {
        this("", "", "", "", "", "", "", "", "", "", "", "");
    }

    public ContactData withId(String id) {
        return new ContactData(id, this.firstName, this.lastName, this.mobilePhone, this.email, this.photo, this.homePhone, this.workPhone, this.secondaryPhone, this.emailSecond, this.emailThird, this.address);
    }

    public ContactData withFirstName(String firstName) {
        return new ContactData(this.id, firstName, this.lastName, this.mobilePhone, this.email, this.photo, this.homePhone, this.workPhone, this.secondaryPhone, this.emailSecond, this.emailThird, this.address);
    }

    public ContactData withLastName(String lastName) {
        return new ContactData(this.id, this.firstName, lastName, this.mobilePhone, this.email, this.photo, this.homePhone, this.workPhone, this.secondaryPhone, this.emailSecond, this.emailThird, this.address);
    }

    public ContactData withMobilePhone(String mobilePhone) {
        return new ContactData(this.id, this.firstName, this.lastName, mobilePhone, this.email, this.photo, this.homePhone, this.workPhone, this.secondaryPhone, this.emailSecond, this.emailThird, this.address);
    }

    public ContactData withEmail(String email) {
        return new ContactData(this.id, this.firstName, this.lastName, this.mobilePhone, email, this.photo, this.homePhone, this.workPhone, this.secondaryPhone, this.emailSecond, this.emailThird, this.address);
    }

    public ContactData withPhoto(String photo) {
        return new ContactData(this.id, this.firstName, this.lastName, this.mobilePhone, this.email, photo, this.homePhone, this.workPhone, this.secondaryPhone, this.emailSecond, this.emailThird, this.address);
    }
}
