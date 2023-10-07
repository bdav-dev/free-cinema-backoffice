package cli.lambdaTerminal.theme;

import java.awt.Color;

public class Theme {

    private static Themes currentThemeEnum = Themes.Light;
    private static TerminalTheme currentTheme = currentThemeEnum.get();

    private Theme() {
    }

    public static TerminalTheme get() {
        return currentTheme;
    }

    public static void set(Themes theme) {
        currentThemeEnum = theme;
        currentTheme = theme.get();
    }

    public static Themes getCurrentThemeAsEnumType() {
        return currentThemeEnum;
    }

    public static enum Themes {
        Dark(new TerminalTheme() {
            @Override
            public Color panelBackgroundColor() {
                return new Color(0x191919);
            }

            @Override
            public Color consoleBackgroundColor() {
                return new Color(0x0C0C0C);
            }

            @Override
            public Color commandLineBackgroundColor() {
                return new Color(0x0C0C0C);
            }

            @Override
            public Color textColor() {
                return Color.WHITE;
            }

            @Override
            public Color scrollBarThumbColor() {
                return Color.DARK_GRAY;
            }

            @Override
            public Color scrollBarTrackColor() {
                return Color.BLACK;
            }
        }),

        Light(new TerminalTheme() {
            @Override
            public Color panelBackgroundColor() {
                return new Color(231, 231, 231);
            }

            @Override
            public Color consoleBackgroundColor() {
                return Color.WHITE;
            }

            @Override
            public Color commandLineBackgroundColor() {
                return Color.WHITE;
            }

            @Override
            public Color textColor() {
                return Color.BLACK;
            }

            @Override
            public Color scrollBarThumbColor() {
                return Color.LIGHT_GRAY;
            }

            @Override
            public Color scrollBarTrackColor() {
                return new Color(243, 243, 243);
            }
        }),

        QuartzDark(new TerminalTheme() {

            @Override
            public Color panelBackgroundColor() {
                return new Color(41, 49, 58);
            }

            @Override
            public Color consoleBackgroundColor() {
                return new Color(28, 34, 40);
            }

            @Override
            public Color commandLineBackgroundColor() {
                return new Color(28, 34, 40);
            }

            @Override
            public Color textColor() {
                return new Color(192, 244, 250);
            }

            @Override
            public Color scrollBarThumbColor() {
                return new Color(73, 87, 103);
            }

            @Override
            public Color scrollBarTrackColor() {
                return new Color(33, 39, 46);
            }

        });

        TerminalTheme theme;

        private Themes(TerminalTheme theme) {
            this.theme = theme;
        }

        public TerminalTheme get() {
            return theme;
        }

    }
}
