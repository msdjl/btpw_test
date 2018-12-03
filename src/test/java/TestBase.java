import entities.User;
import io.github.bonigarcia.SeleniumExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

import static utils.Config.WINDOW_SIZE;

@ExtendWith(SeleniumExtension.class)
class TestBase {
    final double DELTA = 0.001;
    WebDriver driver;
    MainPage mainPage;

    private TestBase() {}

    TestBase(WebDriver driver) {
        this.driver = driver;
        driver.manage().window().setSize(WINDOW_SIZE);
    }

    void open(String url) {
        driver.get(url);
        mainPage = new MainPage(driver);
    }

    void login(User user) {
        mainPage.openLoginPage().login(user);
    }

    void openAndLogin(String url, User user) {
        open(url);
        login(user);
    }

    void clearCookies() {
        driver.manage().deleteAllCookies();
    }
}
