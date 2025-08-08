package view;

import model.QuizSection;
import model.Question;

import javax.swing.*;
import java.awt.*;

/**
 * SectionPanel supports two modes:
 *  - viewer mode: new SectionPanel(section)
 *  - editor mode:  new SectionPanel(section, true, onChangeCallback)
 */
public class SectionPanel extends JPanel {
    public SectionPanel(QuizSection s) {
        this(s, false, null);
    }

    public SectionPanel(QuizSection s, boolean editable, Runnable onChange) {
        setLayout(new BorderLayout(6, 6));

        JLabel title = new JLabel(s.getTitle());
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.WEST);

        if (editable) {
            JButton addQ = new JButton("Add Question");
            addQ.addActionListener(e -> showAddQuestionDialog(s, onChange));
            top.add(addQ, BorderLayout.EAST);
        }

        add(top, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        int idx = 0;
        for (Question q : s.getQuestions()) {
            if (editable) {
                JPanel row = new JPanel(new BorderLayout());
                JLabel lbl = new JLabel((idx + 1) + ". " + q.getPrompt());
                row.add(lbl, BorderLayout.CENTER);
                JButton del = new JButton("Delete");
                del.addActionListener(ev -> {
                    s.getQuestions().remove(q);
                    if (onChange != null) onChange.run();
                });
                row.add(del, BorderLayout.EAST);
                list.add(row);
                list.add(Box.createVerticalStrut(6));
            } else {
                list.add(new QuestionPanel(q));
                list.add(Box.createVerticalStrut(6));
            }
            idx++;
        }

        add(list, BorderLayout.CENTER);
    }

    private void showAddQuestionDialog(QuizSection s, Runnable onChange) {
        JTextField promptField = new JTextField();
        JTextField opt1 = new JTextField();
        JTextField opt2 = new JTextField();
        JTextField opt3 = new JTextField();
        JTextField opt4 = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Prompt:"));
        panel.add(promptField);
        panel.add(new JLabel("Option 1:"));
        panel.add(opt1);
        panel.add(new JLabel("Option 2:"));
        panel.add(opt2);
        panel.add(new JLabel("Option 3 (optional):"));
        panel.add(opt3);
        panel.add(new JLabel("Option 4 (optional):"));
        panel.add(opt4);

        int res = JOptionPane.showConfirmDialog(this, panel, "Add Question", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            java.util.List<String> opts = new java.util.ArrayList<>();
            if (!opt1.getText().trim().isEmpty()) opts.add(opt1.getText().trim());
            if (!opt2.getText().trim().isEmpty()) opts.add(opt2.getText().trim());
            if (!opt3.getText().trim().isEmpty()) opts.add(opt3.getText().trim());
            if (!opt4.getText().trim().isEmpty()) opts.add(opt4.getText().trim());

            if (promptField.getText().trim().isEmpty() || opts.size() < 2) {
                JOptionPane.showMessageDialog(this, "Please enter a prompt and at least two options.");
                return;
            }

            Object chosen = JOptionPane.showInputDialog(this, "Select correct option:", "Correct Option", JOptionPane.PLAIN_MESSAGE, null, opts.toArray(), opts.get(0));
            int correct = opts.indexOf(chosen);

            s.addQuestion(new model.Question(promptField.getText().trim(), opts, correct));
            if (onChange != null) onChange.run();
        }
    }
}
