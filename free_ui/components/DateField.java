package free_ui.components;

import javax.swing.JTextField;

import fcbo.datatypes.time.Date;

@SuppressWarnings("serial")
public class DateField extends JTextField {

    public DateField() {
        super();

    }

    public Date getDate() {
        Date date = new Date(getText());
        return date;
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        // setFont(AssetManager.get().getDefaultMonoFont().deriveFont(height - 10.0f));
    }

}
