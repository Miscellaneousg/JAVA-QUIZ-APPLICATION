package view;

import app.QuizApp;
import app.UIUtils;
import model.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.util.List;

public class DashboardPanel extends JPanel {
    private QuizApp app;
    private JPanel listPanel;

    public DashboardPanel(QuizApp app) {
        this.app = app;
        setLayout(new BorderLayout(10, 10));
        add(UIUtils.title("Welcome to Quizava"), BorderLayout.NORTH);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(listPanel);
        add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton newQuiz = UIUtils.button("Create New Quiz");
        newQuiz.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(this, "Quiz name:");
            if (name != null && !name.trim().isEmpty()) {
                Quiz q = new Quiz(name.trim());
                app.getQuizzes().add(q);
                app.showQuizEditor(q);
                rebuildList();
            }
        });
        bottom.add(newQuiz);
        add(bottom, BorderLayout.SOUTH);

        // rebuild when panel becomes visible (keeps list up-to-date)
        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing()) {
                rebuildList();
            }
        });

        rebuildList();
    }

    private void rebuildList() {
        listPanel.removeAll();
        List<Quiz> quizzes = app.getQuizzes();
        if (quizzes.isEmpty()) {
            JLabel none = new JLabel("No quizzes yet. Create one!");
            none.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPanel.add(none);
        } else {
            for (Quiz q : quizzes) {
                JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel name = new JLabel(q.getName());
                JButton start = UIUtils.button("Start");
                start.addActionListener(e -> app.showQuiz(q));
                JButton edit = UIUtils.button("Edit");
                edit.addActionListener(e -> app.showQuizEditor(q));
                row.add(name);
                row.add(start);
                row.add(edit);
                listPanel.add(row);
            }
        }
        listPanel.revalidate();
        listPanel.repaint();
    }
}
