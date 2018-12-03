package pages;

import entities.User;
import org.openqa.selenium.WebDriver;

public class LoginPage extends PageBase {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(User user) {
        $("#input_phone").sendKeys(user.getPhoneWithoutCountryCode());
        $("#input_password").sendKeys(user.getPassword());
        $("#input_password").submit();
        wait.until((WebDriver d) -> this.isLoggedIn());
    }
}
