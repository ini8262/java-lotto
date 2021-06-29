package lotto.util;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {
    private static final String DEFAULT_REGEX = "\\s*,\\s*";
    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("###,###,###,###");

    private StringUtils() {
        throw new AssertionError();
    }

    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }

    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }

    public static int[] csvToIntArray(String source) {
        return csvToIntArray(source, DEFAULT_REGEX);
    }

    public static List<Integer> csvToIntegerList(String source) {
        return csvToIntegerList(source, DEFAULT_REGEX);
    }

    public static int[] csvToDistinctIntArray(String source) {
        return csvToDistinctIntArray(source, DEFAULT_REGEX);
    }

    public static int[] csvToDistinctIntArray(String source, String regex) {
        return Arrays.stream(source.trim().split(regex))
                .mapToInt(Integer::parseInt)
                .distinct()
                .toArray();
    }

    public static List<Integer> csvToIntegerList(String source, String regex) {
        return Arrays.stream(source.trim().split(regex))
                .mapToInt(Integer::parseInt)
                .boxed()
                .distinct()
                .collect(Collectors.toList());
    }

    public static int[] csvToIntArray(String source, String regex) {
        return Arrays.stream(source.trim().split(regex))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static String toMoneyFormat(double money) {
        return MONEY_FORMAT.format(money);
    }

}
