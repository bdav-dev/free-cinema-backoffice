package db;

import java.util.Optional;

import utility.Utility;

public enum Mandt {
    
    DEVELOP(701),
    TEST(203),
    PRODUCTION(304827);

    public final int value;

    Mandt(int value) {
        this.value = value;
    }

    public static Optional<Mandt> fromString(String string) {
        for(var mandt : Mandt.values()) {
            var intValue = Utility.parseInt(string);

            if (intValue.isPresent() && intValue.get() == mandt.value) {
                return Optional.of(mandt);
            }
        }

        return Optional.empty();
    }

}
