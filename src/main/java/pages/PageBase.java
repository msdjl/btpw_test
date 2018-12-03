package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.URI;
import java.net.URISyntaxException;

public class PageBase extends Element {
    private final String MENU_SELECTOR = "#Main-Menu-Button";
    private RightSidePanel rightSidePanel;

    public PageBase(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(MENU_SELECTOR)));
        rightSidePanel = new RightSidePanel(driver);
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

    public UpcomingEventsPage openUpcomingEventsPage() {
        $("#left-side-sports ul.nav").findElement(By.partialLinkText("NEXT 48 HOURS")).click();
        return new UpcomingEventsPage(driver);
    }

    public boolean isLoggedIn() {
        return rightSidePanel.isLoggedIn();
    }

    public float getBalance() {
        return rightSidePanel.getBalance();
    }

    public RightSidePanel getRightSidePanel() {
        return rightSidePanel;
    }
}
