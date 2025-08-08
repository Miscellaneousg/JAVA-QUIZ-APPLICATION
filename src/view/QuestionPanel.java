package view;

import model.Question;

import javax.swing.*;
import java.awt.*;

/**
 * Simple question display for quiz-taking mode.
 * It uses radio buttons and writes user's selection back into the Question model.
 */
public class QuestionPanel extends JPanel {
    public QuestionPanel(Question q) {
        setLayout(new BorderLayout(4, 4));
        JLabel prompt = new JLabel("<html><b>" + q.getPrompt() + "</b></html>");
        add(prompt, BorderLayout.NORTH);

        ButtonGroup bg = new ButtonGroup();
        JPanel opts = new JPanel();
        opts.setLayout(new BoxLayout(opts, BoxLayout.Y_AXIS));

        int idx = 0;
        for (String option : q.getOptions()) {
            JRadioButton rb = new JRadioButton(option);
            final int choice = idx;
            if (q.getUserAnswer() == choice) rb.setSelected(true);
            rb.addActionListener(e -> q.setUserAnswer(choice));
            bg.add(rb);
            opts.add(rb);
            idx++;
        }

        add(opts, BorderLayout.CENTER);
    }
}