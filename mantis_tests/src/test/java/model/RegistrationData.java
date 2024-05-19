package model;

public record RegistrationData(String username, String password) {
    public RegistrationData() {
        this("", "");
    }

    public RegistrationData withUserName(String username) {
        return new RegistrationData(username, this.password);
    }

    public RegistrationData withPassword(String password) {
        return new RegistrationData(this.username, password);
    }
}
