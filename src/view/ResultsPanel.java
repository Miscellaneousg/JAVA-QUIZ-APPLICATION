package view;

import app.QuizApp;
import app.UIConfig;
import app.UIUtils;
import app.PanelNames;
import model.Quiz;
import model.QuizSection;
import model.Question;

import javax.swing.*;
import java.awt.*;

public class ResultsPanel extends JPanel {
    private QuizApp app;
    private JPanel center;

    public ResultsPanel(QuizApp app) {
        this.app = app;
        setLayout(new BorderLayout(10, 10));
        add(UIUtils.title("Results"), BorderLayout.NORTH);

        center = new JPanel(new BorderLayout());
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton back = UIUtils.button("Back to Dashboard");
        back.addActionListener(e -> app.showPanel(PanelNames.DASHBOARD));
        bottom.add(back);
        add(bottom, BorderLayout.SOUTH);
    }

    public void showQuizResults(Quiz quiz) {
        center.removeAll();

        int percent = quiz.scorePercent();
        JLabel percentLabel = new JLabel(percent + "%");
        percentLabel.setFont(UIConfig.TITLE_FONT);
        percentLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JProgressBar bar = UIUtils.scoreBar(percent);

        JPanel top = new JPanel(new BorderLayout());
        top.add(percentLabel, BorderLayout.NORTH);
        top.add(bar, BorderLayout.CENTER);

        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        for (QuizSection s : quiz.getSections()) {
            JLabel sect = new JLabel(s.getTitle());
            sect.setFont(sect.getFont().deriveFont(Font.BOLD));
            details.add(sect);
            details.add(Box.createVerticalStrut(6));
            for (Question q : s.getQuestions()) {
                String user = (q.getUserAnswer() >= 0 && q.getUserAnswer() < q.getOptions().size()) ? q.getOptions().get(q.getUserAnswer()) : "<no answer>";
                String correct = q.getOptions().get(q.getCorrectAnswer());
                JLabel qlbl = new JLabel("<html><b>" + q.getPrompt() + "</b><br/>Your answer: " + user + "<br/>Correct: " + correct + "</html>");
                if (q.getUserAnswer() == q.getCorrectAnswer()) {
                    qlbl.setForeground(UIConfig.SUCCESS_COLOR);
                } else {
                    qlbl.setForeground(UIConfig.ERROR_COLOR);
                }
                details.add(qlbl);
                details.add(Box.createVerticalStrut(8));
            }
            details.add(Box.createVerticalStrut(10));
        }

        JScrollPane scroll = new JScrollPane(details);
        center.add(top, BorderLayout.NORTH);
        center.add(scroll, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}