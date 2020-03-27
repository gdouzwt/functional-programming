package functional;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Concordance {

    private static final Pattern WORD_BREAK = Pattern.compile("\\W+");
    private static final Comparator<Map.Entry<String, Long>> valueOrder
            = Map.Entry.comparingByValue();

    public static void main(String[] args) {
        List<String> filenames =
                Arrays.asList("PrideAndPrejudice.txt", "Bad.txt", "Emma.txt", "SenseAndSensibility.txt");
        filenames.stream()
                .map(Paths::get)
                .map(Either.wrap(Files::lines))
                .peek(e -> e.handle(System.err::println))
                .filter(e -> e.success())
                .flatMap(Either::get)
                .map(String::toLowerCase)
                .flatMap(WORD_BREAK::splitAsStream)
                .filter(s -> s.length() > 0)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(valueOrder.reversed())
                .limit(200)
                .map(e -> String.format("%20s : %5d", e.getKey(), e.getValue()))
                .forEach(System.out::println);
    }
}

