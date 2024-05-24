package common;

import java.util.Random;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonFunctions {
    public static String randomString(int n) {
        var rnd = new Random();
        Supplier<Integer> randomNumbers = () -> rnd.nextInt(26);
        return Stream.generate(randomNumbers)
                .limit(3)
                .map(i -> 'a' + i)
                .map(Character::toString)
                .collect(Collectors.joining());
    }

    public static String extractUrl(String message) {
        var url = "";
        var pattern = Pattern.compile("http://\\S*" );
        var matcher = pattern.matcher(message);
        if (matcher.find()) {
            url = message.substring(matcher.start(), matcher.end());
        }
        return url;
    }
}
