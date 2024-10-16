package exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DisplayableException extends Exception {
    private String description;
    private String title;

    public DisplayableException(String title, String description) {
        super();
        this.description = description;
        this.title = title;
    }

    public static DisplayableException fromException(Exception e) {
        return new DisplayableException("Fehler", e.getMessage() + "\n\nStacktrace:\n" + Arrays.stream(e.getStackTrace()).map(a -> a.toString()).collect(Collectors.joining("\n")));
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getMessage() {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(description));

        list.add(0, title + ":");

        return String.join(" ", list.toArray(String[]::new));
    }
}
