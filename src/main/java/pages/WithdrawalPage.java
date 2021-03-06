package pages;

import entities.Voucher;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static utils.Utils.getDouble;
import static utils.Utils.sleep;

public class WithdrawalPage extends PageBase {
    private final String PAYOUT_PANEL_SELECTOR = "#Payout-Request-Panel";
    private final String NOTIFICATION_SELECTOR = PAYOUT_PANEL_SELECTOR + " .notify";

    public WithdrawalPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(PAYOUT_PANEL_SELECTOR)));
    }

    public void switchToVoucherTab() {
        $("#withdraw-to-voucher").click();
    }

    public void withdrawToVoucher(int amount) {
        $("#Input-Amount").sendKeys(String.valueOf(amount));
        $("#Submit-Payout").click();
        wait.until((WebDriver d) -> this.isWithdrawalFinished());
        sleep(1000);
    }

    public String getNotificationText() {
        return $(NOTIFICATION_SELECTOR).getText();
    }

    public Voucher getNewVoucherInfoFromNotification() {
        Voucher voucher = new Voucher();
        String regex = "(\\d+) (\\w+) .* ((\\d{3}-){3}\\d{3})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(getNotificationText());
        if (matcher.find()) {
            voucher.setAmount(parseInt(matcher.group(1)));
            voucher.setCurrency(matcher.group(2));
            voucher.setNumber(matcher.group(3));
        }
        return voucher;
    }

    public Voucher getNewVoucherInfoFromStatement() {
        Voucher voucher = new Voucher();
        WebElement row = $(".pending-reques .row-bottomline");
        List<WebElement> cells = row.findElements(By.cssSelector(".cell"));
        voucher.setNumber(cells.get(1).getText());
        voucher.setAmount(getDouble(cells.get(2).getText()));
        return voucher;
    }

    public boolean isWithdrawalFinished() {
        return !$$(NOTIFICATION_SELECTOR + ":not(.hidden)").isEmpty();
    }

    public boolean isWithdrawalSuccessful() {
        return !$$(NOTIFICATION_SELECTOR + ".success").isEmpty();
    }
}
