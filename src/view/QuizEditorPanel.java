package view;

import app.QuizApp;
import app.UIUtils;
import app.PanelNames;
import model.Quiz;
import model.QuizSection;

import javax.swing.*;
import java.awt.*;

public class QuizEditorPanel extends JPanel {
    private Quiz quiz;
    private QuizApp app;
    private JPanel content;

    public QuizEditorPanel(QuizApp app, Quiz quiz) {
        this.app = app;
        this.quiz = quiz;
        setLayout(new BorderLayout(8, 8));
        add(UIUtils.title("Edit Quiz: " + quiz.getName()), BorderLayout.NORTH);

        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        add(new JScrollPane(content), BorderLayout.CENTER);

        JPanel bot = new JPanel();
        JButton addSection = UIUtils.button("Add Section");
        addSection.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(this, "Section title:");
            if (title != null && !title.trim().isEmpty()) {
                quiz.addSectionFromTitle(title.trim());
                refresh();
            }
        });

        JButton save = UIUtils.button("Save & Back");
        save.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Saved (in-memory).");
            app.showPanel(PanelNames.DASHBOARD);
        });

        bot.add(addSection);
        bot.add(save);
        add(bot, BorderLayout.SOUTH);

        refresh();
    }

    private void refresh() {
        content.removeAll();
        if (quiz.getSections().isEmpty()) {
            JLabel hint = new JLabel("No sections yet. Use 'Add Section' to start.");
            hint.setAlignmentX(Component.CENTER_ALIGNMENT);
            content.add(hint);
        } else {
            for (QuizSection s : quiz.getSections()) {
                content.add(new SectionPanel(s, true, this::refresh));
                content.add(UIUtils.vGap(8));
            }
        }
        content.revalidate();
        content.repaint();
    }
}