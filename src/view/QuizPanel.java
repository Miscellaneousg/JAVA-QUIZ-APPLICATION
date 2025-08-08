package view;

import app.QuizApp;
import app.UIUtils;
import model.Quiz;
import model.QuizSection;

import javax.swing.*;
import java.awt.*;

public class QuizPanel extends JPanel {
    private Quiz quiz;
    private QuizApp app;

    public QuizPanel(QuizApp app, Quiz quiz) {
        this.app = app;
        this.quiz = quiz;

        setLayout(new BorderLayout(10, 10));
        add(UIUtils.title("Quiz: " + quiz.getName()), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        if (quiz.getSections().isEmpty()) {
            JLabel empty = new JLabel("This quiz has no sections/questions. Open the editor to add content.");
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            content.add(empty);
        } else {
            for (QuizSection s : quiz.getSections()) {
                SectionPanel sp = new SectionPanel(s); // non-editable viewer mode
                content.add(sp);
                content.add(UIUtils.vGap(10));
            }
        }

        JButton submit = UIUtils.button("Submit");
        submit.addActionListener(e -> {
            // simple submit - show results
            app.showQuizResults(quiz);
        });

        JButton back = UIUtils.button("Back");
        back.addActionListener(e -> app.showPanel("Dashboard"));

        JPanel bottom = new JPanel();
        bottom.add(back);
        bottom.add(submit);

        add(new JScrollPane(content), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}