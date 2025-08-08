package app;

import view.DashboardPanel;
import view.QuizPanel;
import view.ResultsPanel;
import view.QuizEditorPanel;
import model.Quiz;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class QuizApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private List<Quiz> quizzes = new ArrayList<>();
    private Set<String> registeredPanels = new HashSet<>();
    private ResultsPanel resultsPanel;

    public QuizApp() {
        super("Quizava — Java OOP Quiz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel, BorderLayout.CENTER);

        // sample data
        quizzes.add(createSampleJavaQuiz());

        // Panels: dashboard always registered
        addPanel(new DashboardPanel(this), PanelNames.DASHBOARD);

        // results panel - keep a reference so we can set results
        resultsPanel = new ResultsPanel(this);
        addPanel(resultsPanel, PanelNames.RESULTS);

        // show dashboard by default
        showPanel(PanelNames.DASHBOARD);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    public void addPanel(JPanel p, String name) {
        if (registeredPanels.contains(name)) return; // avoid duplicates
        mainPanel.add(p, name);
        registeredPanels.add(name);
    }

    public void showQuiz(Quiz quiz) {
        String name = PanelNames.QUIZ + "-" + quiz.getName();
        if (!registeredPanels.contains(name)) {
            addPanel(new QuizPanel(this, quiz), name);
        }
        showPanel(name);
    }

    public void showQuizEditor(Quiz quiz) {
        String name = PanelNames.QUIZ_EDITOR + "-" + quiz.getName();
        if (!registeredPanels.contains(name)) {
            addPanel(new QuizEditorPanel(this, quiz), name);
        }
        showPanel(name);
    }

    public void showQuizResults(Quiz quiz) {
        resultsPanel.showQuizResults(quiz);
        showPanel(PanelNames.RESULTS);
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    private Quiz createSampleJavaQuiz() {
        Quiz q = new Quiz("JavaBasics");
        q.addSectionFromTitle("OOP Concepts");
        q.getSections().get(0).addQuestion(
                new model.Question("Which keyword is used for inheritance in Java?",
                        java.util.Arrays.asList("implements", "extends", "inherits", "super"), 1)
        );

        q.getSections().get(0).addQuestion(
                new model.Question("What does OOP stand for?",
                        java.util.Arrays.asList("Object-Oriented Programming", "Only OOP", "Open-OP", "Overloaded Objects"), 0)
        );
        return q;
    }
}
