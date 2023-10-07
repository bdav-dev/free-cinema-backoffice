package appsettings;

@FunctionalInterface
public interface FromString<T> {
    T convert(String string, T fallback);


    public static class Primitives {

        private Primitives() {}

        public final static FromString<String> STRING = (string, fallback) -> {
            return string;
        };

        public final static FromString<Integer> INTEGER = (string, fallback) -> {
            try {
                return Integer.valueOf(string);
            } catch (Exception e) {
                return fallback;
            }
        };

        public final static FromString<Double> DOUBLE = (string, fallback) -> {
            try {
                return Double.valueOf(string);
            } catch (Exception e) {
                return fallback;
            }
        };

        public final static FromString<Boolean> BOOLEAN = (string, fallback) -> {
            try {
                if(string.equals("1") || string.toLowerCase().equals("true"))
                    return true;
                    
                return false;
            } catch (Exception e) {
                return fallback;
            }
        };

    }
}
