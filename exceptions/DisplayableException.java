package exceptions;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("serial")
public abstract class DisplayableException extends Exception {
    private String description;
    private String title;

    public DisplayableException(String title, String description) {
        super();
        this.description = description;
        this.title = title;
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
