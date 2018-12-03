import entities.Statement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.RightSidePanel;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Config.UGANDAN_WITH_BALANCE;
import static utils.Config.UGANDA_URL;
import static utils.Utils.getRandomInt;

public class BetsTest extends TestBase {
    int amount;
    float initialBalance;
    RightSidePanel panel;

    public BetsTest(WebDriver driver) {
        super(driver);
    }

    @BeforeEach
    void beforeEach() {
        openAndLogin(UGANDA_URL, UGANDAN_WITH_BALANCE);
        amount = getRandomInt(1, 50);
        initialBalance = mainPage.getBalance();
        panel = mainPage.getRightSidePanel();
    }

    @Test
    @DisplayName("Successful bet placement")
    void successfulBetPlacement() {
        mainPage.openUpcomingEventsPage().clickFirstSelection();
        panel.setStake(amount);
        placeBetAndCheckResults();
    }

    @Test
    @DisplayName("Successful multi bet with bonus placement")
    void successfulMultiBetWithBonusPlacement() {
        mainPage.openUpcomingEventsPage().clickFirstXSelections(5);
        panel.setStake(amount);

        float odds = panel.getOdds();
        float potentialWinnings = panel.getPotentialWinnings();
        float bonus = panel.getBonus();
        float payout = panel.getPayout();

        float bigDelta = 1; // because, as I can see, there is a difference between real and displayed odds: https://imgur.com/a/PlZ7DnV
        assertAll("bonus and payout are calculated right",
                () -> assertEquals(odds * amount, potentialWinnings, bigDelta, "potential winnings value is correct"),
                () -> assertEquals(potentialWinnings * 0.1, bonus, DELTA, "bonus value is correct"),
                () -> assertEquals(potentialWinnings + bonus, payout, DELTA, "payout value is correct"));

        placeBetAndCheckResults();
    }

    void placeBetAndCheckResults() {
        panel.placeBet();
        float resultBalance = mainPage.getBalance();
        assertAll("the bet is placed",
                () -> assertTrue(panel.isPlacementSuccessful(), "there is a success notification"),
                () -> assertEquals(initialBalance - amount, resultBalance, DELTA));

        Statement statement = mainPage.openStatementsPage().getLastStatement();
        assertAll("statement is added",
                () -> assertEquals(resultBalance, statement.getBalance(), DELTA),
                () -> assertEquals(-amount, statement.getChange(), DELTA));
    }
}
