package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static float getFloat(String str) {
        Pattern pattern = Pattern.compile("-?\\d+(?:\\.\\d+)?");
        Matcher matcher = pattern.matcher(str.replaceAll(" ", ""));
        if(matcher.find()){
            return Float.valueOf(matcher.group());
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
}
