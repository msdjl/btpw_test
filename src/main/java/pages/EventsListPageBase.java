package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class EventsListPageBase extends PageBase {
    public EventsListPageBase(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".events-container.prematch")));
    }

    public List<WebElement> getEventsList() {
        return $$(".events-container.prematch");
    }

    public void clickFirstSelection() {
        clickFirstXSelections(1);
    }

    public void clickFirstXSelections(int amount) {
        List<WebElement> events = getEventsList();
        for(int i = 0; i < amount; i++) {
            events.get(i).findElement(By.cssSelector(".event-bet a")).click();
        }
    }
}
