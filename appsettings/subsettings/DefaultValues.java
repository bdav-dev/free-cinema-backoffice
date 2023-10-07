package appsettings.subsettings;

import appsettings.FromString;
import appsettings.LocalStorageSetting;

public class DefaultValues {
    private static final DefaultValues instance = new DefaultValues();

    private LocalStorageSetting<Double> defaultMwst;
    private LocalStorageSetting<Double> defaultTicketprice;

    private DefaultValues() {
        defaultMwst = new LocalStorageSetting<Double>(
                "default_mwst",
                0.07,
                FromString.Primitives.DOUBLE);

        defaultTicketprice = new LocalStorageSetting<Double>(
                "default_ticket_price",
                5d,
                FromString.Primitives.DOUBLE);
    }

    public static DefaultValues getInstance() {
        return instance;
    }

    public LocalStorageSetting<Double> defaultMwst() {
        return defaultMwst;
    }

    public LocalStorageSetting<Double> defaultTicketprice() {
        return defaultTicketprice;
    }

}
