import entities.Statement;
import entities.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.WithdrawalPage;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Config.UGANDAN_WITH_BALANCE;
import static utils.Config.UGANDA_URL;
import static utils.Utils.getRandomInt;

class WithdrawalTest extends TestBase {
    public WithdrawalTest(WebDriver driver) {
        super(driver);
    }

    @BeforeEach
    void beforeEach() {
        openAndLogin(UGANDA_URL, UGANDAN_WITH_BALANCE);
    }

    @Test
    @DisplayName("Successful withdrawal to voucher")
    void successfulWithdrawalToVoucher() {
        int amount = getRandomInt(1, 100);
        float balance = mainPage.getBalance();
        WithdrawalPage wp = mainPage.openWithdrawalPage();
        wp.switchToVoucherTab();
        wp.withdrawToVoucher(amount);
        float resultBalance = mainPage.getBalance();
        Voucher voucherInfoFromNotification = wp.getNewVoucherInfoFromNotification();
        Voucher voucherInfoFromStatement = wp.getNewVoucherInfoFromStatement();

        assertEquals(balance - amount, resultBalance, DELTA, "balance is decreased");
        assertAll("voucher is created",
                () -> assertTrue(wp.isWithdrawalSuccessful(), "there is a success notification"),
                () -> assertEquals(voucherInfoFromNotification.getAmount(), voucherInfoFromStatement.getAmount()),
                () -> assertEquals(voucherInfoFromNotification.getNumber(), voucherInfoFromStatement.getNumber()),
                () -> assertEquals(amount, voucherInfoFromNotification.getAmount()));

        Statement statement = mainPage.openStatementsPage().getLastStatement();
        assertAll("statement is added",
                () -> assertEquals(resultBalance, statement.getBalance(), DELTA),
                () -> assertEquals(-amount, statement.getChange(), DELTA));
    }

    @Test
    @DisplayName("Failed withdrawal to voucher in case insufficient balance")
    void failedWithdrawalToVoucherInCaseInsufficientBalance() {
        float balance = mainPage.getBalance();
        int amount = (int)balance + 1;
        WithdrawalPage wp = mainPage.openWithdrawalPage();
        wp.switchToVoucherTab();
        wp.withdrawToVoucher(amount);

        String expectedMessage = "You cannot withdraw more money than you have on your account.";
        assertAll("voucher isn't created because of insufficient balance",
                () -> assertFalse(wp.isWithdrawalSuccessful(), "there is an error notification"),
                () -> assertEquals(balance, mainPage.getBalance(), "balance isn't changed"),
                () -> assertEquals(expectedMessage, wp.getNotificationText()));
    }

    @Test
    @DisplayName("Failed withdrawal in case invalid amount")
    void failedWithdrawalToVoucherInCaseInvalidAmount() {
        float balance = mainPage.getBalance();
        int amount = 0;
        WithdrawalPage wp = mainPage.openWithdrawalPage();
        wp.switchToVoucherTab();
        wp.withdrawToVoucher(amount);

        String expectedMessage = "Invalid amount: please select correct amount.";
        assertAll("voucher isn't created because of wrong amount value",
                () -> assertFalse(wp.isWithdrawalSuccessful(), "there is an error notification"),
                () -> assertEquals(balance, mainPage.getBalance(), "balance isn't changed"),
                () -> assertEquals(expectedMessage, wp.getNotificationText()));
    }
}
