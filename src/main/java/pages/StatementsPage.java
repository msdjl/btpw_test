package pages;

import entities.Statement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static utils.Utils.getFloat;

public class StatementsPage extends PageBase {
    private final String SPINNER_SELECTOR = ".bp-spinner";

    public StatementsPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(SPINNER_SELECTOR)));
        wait.until(ExpectedConditions.invisibilityOf($(SPINNER_SELECTOR)));
    }

    public Statement getLastStatement() {
        Statement statement = new Statement();
        WebElement row = $("table.statement tbody tr");
        List<WebElement> cells = row.findElements(By.cssSelector("td"));
        statement.setChange(getFloat(cells.get(2).getText()));
        statement.setBalance(getFloat(cells.get(3).getText()));
        return statement;
    }
}
