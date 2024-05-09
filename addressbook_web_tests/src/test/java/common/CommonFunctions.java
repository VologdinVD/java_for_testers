package common;

import java.util.Random;
import java.util.function.Supplier;
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
}
