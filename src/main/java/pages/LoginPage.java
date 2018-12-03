package pages;

import entities.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends PageBase {
    private final String PHONE_SELECTOR = "#input_phone";
    private final String PASSWORD_SELECTOR = "#input_password";

    public LoginPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(PHONE_SELECTOR)));
    }

    public void login(User user) {
        $(PHONE_SELECTOR).sendKeys(user.getPhoneWithoutCountryCode());
        $(PASSWORD_SELECTOR).sendKeys(user.getPassword());
        $(PASSWORD_SELECTOR).submit();
        wait.until((WebDriver d) -> this.isLoggedIn());
    }
}
