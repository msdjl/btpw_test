package utils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static double getDouble(String str) {
        Pattern pattern = Pattern.compile("-?\\d+(?:\\.\\d+)?");
        Matcher matcher = pattern.matcher(str.replaceAll(" ", ""));
        if(matcher.find()){
            return Double.valueOf(matcher.group());
        }
        return 0;
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
