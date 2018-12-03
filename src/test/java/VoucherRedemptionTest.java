import entities.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.VoucherPage;
import pages.WithdrawalPage;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Config.*;
import static utils.Utils.getRandomInt;

public class VoucherRedemptionTest extends TestBase {
    public VoucherRedemptionTest(WebDriver driver) {
        super(driver);
    }

    double initialBalanceOfCreator;
    int amount;
    String voucherNumber;

    @BeforeEach
    void beforeEach() {
        openAndLogin(UGANDA_URL, UGANDAN_WITH_BALANCE);
        initialBalanceOfCreator = mainPage.getBalance();
        WithdrawalPage wp = mainPage.openWithdrawalPage();
        wp.switchToVoucherTab();
        amount = getRandomInt(1, 100);
        wp.withdrawToVoucher(amount);
        voucherNumber = wp.getNewVoucherInfoFromNotification().getNumber();
    }

    @Test
    @DisplayName("Successful voucher redemption")
    void successfulVoucherRedemption() {
        double resultBalanceOfCreator = mainPage.getBalance();

        clearCookies();
        openAndLogin(UGANDA_URL, UGANDAN_WITHOUT_BALANCE);
        double initialReceiverBalance = mainPage.getBalance();
        VoucherPage vp = mainPage.openVoucherPage();
        vp.activate(voucherNumber);
        double resultReceiverBalance = mainPage.getBalance();

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
        double initialReceiverBalance = mainPage.getBalance();
        VoucherPage vp = mainPage.openVoucherPage();
        vp.activate(voucherNumber);
        double resultReceiverBalance = mainPage.getBalance();

        String expectedMessage = "The voucher is in UGX. You are only allowed to deposit vouchers in KES.";
        assertAll("voucher isn't activated",
                () -> assertFalse(vp.isActivationSuccessful(), "there is an error notification"),
                () -> assertEquals(initialReceiverBalance, resultReceiverBalance, DELTA),
                () -> assertEquals(expectedMessage, vp.getNotificationText()));
    }
}
