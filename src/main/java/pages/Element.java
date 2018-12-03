package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static utils.Config.TIMEOUT;

public class Element {
    WebDriver driver;
    WebDriverWait wait;

    public Element(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    WebElement $(String selector) {
        return driver.findElement(By.cssSelector(selector));
    }

    List<WebElement> $$(String selector) {
        return driver.findElements(By.cssSelector(selector));
    }
}
