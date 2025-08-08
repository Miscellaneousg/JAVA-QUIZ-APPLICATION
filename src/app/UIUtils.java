package app;

import javax.swing.*;
import java.awt.*;

public class UIUtils {

    public static JLabel title(String text) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(UIConfig.TITLE_FONT);
        return l;
    }

    public static JButton button(String text) {
        JButton b = new JButton(text);
        b.setFont(UIConfig.NORMAL_FONT);
        return b;
    }

    public static JProgressBar scoreBar(int value) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(value);
        bar.setStringPainted(true);
        bar.setPreferredSize(new Dimension(200, 22));
        bar.setForeground(value >= UIConfig.PASS_THRESHOLD ? UIConfig.SUCCESS_COLOR : UIConfig.ERROR_COLOR);
        return bar;
    }

    public static Component vGap(int pixels) {
        return Box.createVerticalStrut(pixels);
    }
}