package src;

import com.diogonunes.jcolor.AnsiFormat;
import static com.diogonunes.jcolor.Attribute.*;

public class Format {
    public static String warning(String message) {
        return new AnsiFormat(BRIGHT_RED_TEXT()).format(message);
    };

    public static String success(String message) {
        return new AnsiFormat(BRIGHT_GREEN_TEXT()).format(message);
    };

    public static String header(String message) {
        return new AnsiFormat(BRIGHT_YELLOW_TEXT(), BOLD()).format(message);
    };

    public static <T extends Number> String highlight(T message) {
        return new AnsiFormat(BRIGHT_YELLOW_TEXT()).format(message.toString());
    };

    public static String highlight(String message) {
        return new AnsiFormat(BRIGHT_YELLOW_TEXT()).format(message.toString());
    };

    public static String highlight(char message) {
        return new AnsiFormat(BRIGHT_YELLOW_TEXT()).format(Character.toString(message));
    };
}
