package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static java.lang.Integer.parseInt;
import static utils.Utils.sleep;

public class VoucherPage extends PageBase {
    private final String VOUCHER_INPUT_SELECTOR = "#input_voucher";
    private final String NOTIFICATION_SELECTOR = ".notify";

    public VoucherPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(VOUCHER_INPUT_SELECTOR)));
    }

    public void activate(String voucherNumber) {
        $(VOUCHER_INPUT_SELECTOR).sendKeys(voucherNumber);
        $("#activateVoucher").click();
        wait.until((WebDriver d) -> this.isActivationFinished());
        sleep(1000);
    }

    public boolean isActivationFinished() {
        return !$$(NOTIFICATION_SELECTOR + ":not(.hidden)").isEmpty();
    }

    public boolean isActivationSuccessful() {
        return !$$(NOTIFICATION_SELECTOR + ".success").isEmpty();
    }

    public String getNotificationText() {
        return $(NOTIFICATION_SELECTOR).getText();
    }

    public int getActivatedVoucherAmount() {
        return parseInt($(".item-yellow").getText());
    }
}
