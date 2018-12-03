package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static utils.Config.TIMEOUT;
import static utils.Utils.getFloat;

public class PageBase {
    WebDriver driver;
    WebDriverWait wait;

    private final String MENU_SELECTOR = "#Main-Menu-Button";

    private PageBase() {}

    public PageBase(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(MENU_SELECTOR)));
    }

    WebElement $(String selector) {
        return driver.findElement(By.cssSelector(selector));
    }

    List<WebElement> $$(String selector) {
        return driver.findElements(By.cssSelector(selector));
    }

    void openHeaderMenu() {
        $(MENU_SELECTOR).click();
    }

    public LoginPage openLoginPage() {
        openHeaderMenu();
        $("#log-in-menu-top").click();
        return new LoginPage(driver);
    }

    public WithdrawalPage openWithdrawalPage() {
        openHeaderMenu();
        $("#withdraw-menu-top").click();
        return new WithdrawalPage(driver);
    }

    public StatementsPage openStatementsPage() {
        openHeaderMenu();
        $("#statement-menu-top").click();
        return new StatementsPage(driver);
    }

    public VoucherPage openVoucherPage() {
        URI uri = null;
        try {
            uri = new URI(driver.getCurrentUrl());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        driver.get(uri != null ? uri.resolve("/voucher").toString() : null);
        return new VoucherPage(driver);
    }

    boolean isLoggedIn() {
        return $$("#join-now-link").isEmpty();
    }

    public float getBalance() {
        return getFloat($(".balance .count").getText());
    }
}
