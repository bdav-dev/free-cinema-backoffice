package free_ui.components;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.*;

import assets.AssetManager;

@SuppressWarnings("serial")
public class PriceField extends JPanel {
    private JTextField priceField;
    private JLabel currencySymbol;

    private Font font;

    public PriceField() {
        priceField = new JTextField();
        currencySymbol = new JLabel();

        AssetManager assetManager = AssetManager.get();
        //font = assetManager.getDefaultMonoFont().deriveFont(12.0f);

        super.setLayout(null);
        super.setBackground(null);

        currencySymbol.setHorizontalTextPosition(SwingConstants.CENTER);
        currencySymbol.setVerticalTextPosition(SwingConstants.CENTER);
        currencySymbol.setVerticalAlignment(SwingConstants.CENTER);
        currencySymbol.setHorizontalAlignment(SwingConstants.CENTER);
        currencySymbol.setAlignmentX(SwingConstants.CENTER);
        currencySymbol.setAlignmentY(SwingConstants.CENTER);

        setTextAlignment(SwingConstants.RIGHT);

        currencySymbol.setText("€");

        add(priceField);
        add(currencySymbol);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        int priceFieldWidth = width - height;

        priceField.setFont(font.deriveFont(height - 10.0f));
        currencySymbol.setFont(font.deriveFont(height - 10.0f));

        super.setBounds(x, y, width, height);
        priceField.setBounds(0, 0, priceFieldWidth, height);
        currencySymbol.setBounds(priceFieldWidth + 1, 0, height, height);
    }

    @Override
    public void setBackground(Color backgroundColor) {
        if (priceField == null) {
            super.setBackground(backgroundColor);
        } else {
            priceField.setBackground(backgroundColor);
        }
    }

    public void setPrice(Double newPrice) {
        if (newPrice != null) {
            priceField.setText(String.valueOf(newPrice));
        } else {
            priceField.setText("");
        }
    }

    public Double getPrice() {
        String price = priceField.getText().replace(',', '.');
        Double parsedPrice;

        try {
            parsedPrice = Double.parseDouble(price);
        } catch (Exception e) {
            return null;
        }

        DecimalFormat df = new DecimalFormat("0.00");
        price = df.format(parsedPrice).replace(',', '.');

        return Double.parseDouble(price);

    }

    @Override
    public void setForeground(Color foregroundColor) {
        if (priceField != null) {
            priceField.setForeground(foregroundColor);
        }
    }

    public void setCurrencySymbolColor(Color currencySymbolColor) {
        currencySymbol.setForeground(currencySymbolColor);
    }

    public void setTextAlignment(int alignment) {
        priceField.setHorizontalAlignment(alignment);
    }

}
