package utils;

import entities.User;
import org.openqa.selenium.Dimension;

import static java.lang.Integer.parseInt;

public class Config {
    public static String UGANDA_URL;
    public static User UGANDAN_WITH_BALANCE;
    public static User UGANDAN_WITHOUT_BALANCE;

    public static String KENYA_URL;
    public static User KENYAN_WITHOUT_BALANCE;

    public static Dimension WINDOW_SIZE;
    public static int TIMEOUT;

    public static String getProperty(String property) {
        return System.getProperty(property);
    }

    static {
        UGANDA_URL = getProperty("uganda.url");
        UGANDAN_WITH_BALANCE = new User(getProperty("uganda.userWithBalance.phone"),
                getProperty("uganda.userWithBalance.password"));
        UGANDAN_WITHOUT_BALANCE = new User(getProperty("uganda.userWithoutBalance.phone"),
                getProperty("uganda.userWithoutBalance.password"));

        KENYA_URL = getProperty("kenya.url");
        KENYAN_WITHOUT_BALANCE = new User(getProperty("kenya.userWithoutBalance.phone"),
                getProperty("kenya.userWithoutBalance.password"));

        WINDOW_SIZE = new Dimension(parseInt(getProperty("window.width")), parseInt(getProperty("window.height")));
        TIMEOUT = parseInt(getProperty("timeoutInSeconds"));
    }
}
