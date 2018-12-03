package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static utils.Utils.getDouble;
import static utils.Utils.sleep;

public class RightSidePanel extends Element {
    final String PANEL_SELECTOR = "#right-slide-betslips";
    final String NOTIFICATION_SELECTOR = PANEL_SELECTOR + " .notify";

    public RightSidePanel(WebDriver driver) {
        super(driver);
    }

    public boolean isLoggedIn() {
        return $$("#join-now-link").isEmpty();
    }

    public double getBalance() {
        return getDouble($(PANEL_SELECTOR + " .balance .count").getText());
    }

    public void setStake(int amount) {
        $("#Bp-Stake-Input").sendKeys(String.valueOf(amount));
    }

    public void placeBet() {
        $("#bp-place-bet").click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(PANEL_SELECTOR + " .spinner")));
        sleep(1000);
    }

    public double getOdds() {
        return getDouble($("#Bp-Total-Odds b").getText());
    }

    public double getPotentialWinnings() {
        return getDouble($("#Bp-Total-Winnings b").getText());
    }

    public double getBonus() {
        return getDouble($("#Bp-Bonus-Win-Amount b").getText());
    }

    public double getPayout() {
        return getDouble($("#Bp-Total-Win-Amount b").getText());
    }

    public boolean isPlacementSuccessful() {
        return $(NOTIFICATION_SELECTOR + ".success").isDisplayed();
    }

    public String getNotificationText() {
        return $(NOTIFICATION_SELECTOR).getText();
    }
}
