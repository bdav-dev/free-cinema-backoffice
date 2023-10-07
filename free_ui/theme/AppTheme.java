package free_ui.theme;

import java.awt.Color;

public class AppTheme {

    private static Themes currentThemeEnum = Themes.LIGHT;
    private static Theme currentTheme = currentThemeEnum.get();

    private AppTheme() {
    }

    public static Theme get() {
        return currentTheme;
    }

    public static void set(Themes theme) {
        currentThemeEnum = theme;
        currentTheme = theme.get();
    }

    public static Themes getCurrentThemeAsEnum() {
        return currentThemeEnum;
    }

    public static enum Themes {
        LIGHT(new Theme() {
            @Override
            public Color background() {
                return new Color(0xE7E7E7);
            }

            @Override
            public Color text() {
                return Color.BLACK;
            }

            public Color invertedText() {
                return Color.WHITE;
            }

            @Override
            public Color elementPrimary() {
                return Color.WHITE;
            }

            @Override
            public Color elementSecondary() {
                return new Color(0xEDEDED);
            }

            @Override
            public Color scrollBarThumb() {
                return Color.LIGHT_GRAY;
            }

            @Override
            public Color scrollBarTrack() {
                return new Color(243, 243, 243);
            }
        });

        Theme theme;

        private Themes(Theme theme) {
            this.theme = theme;
        }

        public Theme get() {
            return theme;
        }
    }
}
