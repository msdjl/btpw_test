package pages;

import org.openqa.selenium.WebDriver;

import static java.lang.Integer.parseInt;
import static utils.Utils.sleep;

public class VoucherPage extends PageBase {
    public VoucherPage(WebDriver driver) {
        super(driver);
    }

    public void activate(String voucherNumber) {
        $("#input_voucher").sendKeys(voucherNumber);
        $("#activateVoucher").click();
        wait.until((WebDriver d) -> this.isActivationFinished());
        sleep(1000);
    }

    public boolean isActivationFinished() {
        return !$$(".notify:not(.hidden)").isEmpty();
    }

    public boolean isActivationSuccessful() {
        return !$$(".notify.success").isEmpty();
    }

    public String getNotificationText() {
        return $(".notify").getText();
    }

    public int getActivatedVoucherAmount() {
        return parseInt($(".item-yellow").getText());
    }
}
