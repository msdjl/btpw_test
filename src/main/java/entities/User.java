package entities;

public class User {
    private String phone;
    private String password;

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getPhoneWithoutCountryCode() {
        return this.phone.substring(4);
    }

    public String getPassword() {
        return this.password;
    }
}
