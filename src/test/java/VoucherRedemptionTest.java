import entities.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.VoucherPage;
import pages.WithdrawalPage;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Config.*;

public class VoucherRedemptionTest extends TestBase {
    public VoucherRedemptionTest(WebDriver driver) {
        super(driver);
    }

    float initialBalanceOfCreator;
    int amount = 50;
    String voucherNumber;

    @BeforeEach
    void beforeEach() {
        openAndLogin(UGANDA_URL, UGANDAN_WITH_BALANCE);
        initialBalanceOfCreator = mainPage.getBalance();
        WithdrawalPage wp = mainPage.openWithdrawalPage();
        wp.switchToVoucherTab();
        wp.withdrawToVoucher(amount);
        voucherNumber = wp.getNewVoucherInfoFromNotification().getNumber();
    }

    @Test
    @DisplayName("Successful voucher redemption")
    void successfulVoucherRedemption() {
        float resultBalanceOfCreator = mainPage.getBalance();

        clearCookies();
        openAndLogin(UGANDA_URL, UGANDAN_WITHOUT_BALANCE);
        float initialReceiverBalance = mainPage.getBalance();
        VoucherPage vp = mainPage.openVoucherPage();
        vp.activate(voucherNumber);
        float resultReceiverBalance = mainPage.getBalance();

        assertEquals(initialBalanceOfCreator - amount, resultBalanceOfCreator, DELTA);
        assertAll("voucher is activated",
                () -> assertTrue(vp.isActivationSuccessful(), "there is a success notification"),
                () -> assertEquals(initialReceiverBalance + amount, resultReceiverBalance, DELTA),
                () -> assertEquals(amount, vp.getActivatedVoucherAmount()));

        Statement statement = mainPage.openStatementsPage().getLastStatement();
        assertAll("statement is added",
                () -> assertEquals(resultReceiverBalance, statement.getBalance(), DELTA),
                () -> assertEquals(amount, statement.getChange(), DELTA));
    }

    @Test
    @DisplayName("Failed voucher redemption in case invalid currency")
    void failedVoucherRedemptionInCaseInvalidCurrency() {
        clearCookies();
        openAndLogin(KENYA_URL, KENYAN_WITHOUT_BALANCE);
        float initialReceiverBalance = mainPage.getBalance();
        VoucherPage vp = mainPage.openVoucherPage();
        vp.activate(voucherNumber);
        float resultReceiverBalance = mainPage.getBalance();

        String expectedMessage = "The voucher is in UGX. You are only allowed to deposit vouchers in KES.";
        assertAll("voucher isn't activated",
                () -> assertFalse(vp.isActivationSuccessful(), "there is an error notification"),
                () -> assertEquals(initialReceiverBalance, resultReceiverBalance, DELTA),
                () -> assertEquals(expectedMessage, vp.getNotificationText()));
        // statement
    }
}
