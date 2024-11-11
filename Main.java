// Main.java
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizApp app = new QuizApp();
            app.setVisible(true);
        });
    }
}

// QuizApp.java
class QuizApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ArrayList<Quiz> quizzes;
    
    public QuizApp() {
        quizzes = new ArrayList<>();
        // Add sample quiz
        createSampleJavaQuiz();
        
        setTitle("Java Quiz Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create and add panels
        DashboardPanel dashboardPanel = new DashboardPanel(this);
        mainPanel.add(dashboardPanel, "Dashboard");
        
        add(mainPanel);
    }
    
    private void createSampleJavaQuiz() {
        Quiz javaQuiz = new Quiz("Java Fundamentals");
        
        // OOP Section
        QuizSection oopSection = new QuizSection("OOP Concepts");
        oopSection.addQuestion(new Question(
            "What is encapsulation?",
            new String[]{
                "Bundling of data and methods that operate on that data within a single unit",
                "Inheritance between classes",
                "Method overloading",
                "Creating multiple objects"
            },
            0
        ));
        oopSection.addQuestion(new Question(
            "Which keyword is used to inherit a class?",
            new String[]{"extends", "implements", "inherits", "using"},
            0
        ));
        
        // Syntax Section
        QuizSection syntaxSection = new QuizSection("Java Syntax");
        syntaxSection.addQuestion(new Question(
            "Which of these is a valid Java main method declaration?",
            new String[]{
                "public static void main(String[] args)",
                "public void main(String args)",
                "public static main(String args[])",
                "static void main(String[] args)"
            },
            0
        ));
        
        // Collections Section
        QuizSection collectionsSection = new QuizSection("Collections Framework");
        collectionsSection.addQuestion(new Question(
            "Which interface is the root of the collection hierarchy?",
            new String[]{"Collection", "List", "ArrayList", "Vector"},
            0
        ));
        
        javaQuiz.addSection(oopSection);
        javaQuiz.addSection(syntaxSection);
        javaQuiz.addSection(collectionsSection);
        
        quizzes.add(javaQuiz);
    }
    
    public ArrayList<Quiz> getQuizzes() {
        return quizzes;
    }
    
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
    
    public void addPanel(JPanel panel, String name) {
        mainPanel.add(panel, name);
    }
}

// Quiz.java
// Update Quiz.java to add new methods
class Quiz {
    private String name;
    private ArrayList<QuizSection> sections;
    
    public Quiz(String name) {
        this.name = name;
        this.sections = new ArrayList<>();
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void addSection(QuizSection section) {
        sections.add(section);
    }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<QuizSection> getSections() {
        return sections;
    }
}

// QuizSection.java
// Update QuizSection.java to add new methods
class QuizSection {
    private String name;
    private ArrayList<Question> questions;
    private int score;
    
    public QuizSection(String name) {
        this.name = name;
        this.questions = new ArrayList<>();
        this.score = 0;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void addQuestion(Question question) {
        questions.add(question);
    }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<Question> getQuestions() {
        return questions;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public int getScore() {
        return score;
    }
}

// Question.java
// Update Question.java to add new methods
class Question {
    private String questionText;
    private String[] options;
    private int correctAnswer;
    private int userAnswer = -1;
    
    public Question(String questionText, String[] options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    
    public void setOptions(String[] options) {
        this.options = options;
    }
    
    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    public String getQuestionText() {
        return questionText;
    }
    
    public String[] getOptions() {
        return options;
    }
    
    public int getCorrectAnswer() {
        return correctAnswer;
    }
    
    public void setUserAnswer(int answer) {
        this.userAnswer = answer;
    }
    
    public int getUserAnswer() {
        return userAnswer;
    }
}

// DashboardPanel.java
// DashboardPanel.java - Updated with delete functionality and better quiz management
class DashboardPanel extends JPanel {
    private QuizApp app;
    
    public DashboardPanel(QuizApp app) {
        this.app = app;
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Available Quizzes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel quizListPanel = new JPanel();
        quizListPanel.setLayout(new BoxLayout(quizListPanel, BoxLayout.Y_AXIS));
        
        // Add quiz list with management options
        for (Quiz quiz : app.getQuizzes()) {
            JPanel quizItemPanel = new JPanel();
            quizItemPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
            quizItemPanel.setMaximumSize(new Dimension(600, 50));
            
            JButton quizButton = new JButton(quiz.getName());
            quizButton.setPreferredSize(new Dimension(200, 35));
            quizButton.addActionListener(e -> startQuiz(quiz));
            
            JButton editButton = new JButton("Edit");
            editButton.setPreferredSize(new Dimension(80, 35));
            editButton.addActionListener(e -> editQuiz(quiz));
            
            JButton deleteButton = new JButton("Delete");
            deleteButton.setPreferredSize(new Dimension(80, 35));
            deleteButton.addActionListener(e -> deleteQuiz(quiz));
            
            quizItemPanel.add(quizButton);
            quizItemPanel.add(editButton);
            quizItemPanel.add(deleteButton);
            
            quizListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            quizListPanel.add(quizItemPanel);
        }
        
        JButton addQuizButton = new JButton("Add New Quiz");
        addQuizButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addQuizButton.setMaximumSize(new Dimension(300, 50));
        addQuizButton.addActionListener(e -> showAddQuizDialog());
        quizListPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        quizListPanel.add(addQuizButton);
        
        JScrollPane scrollPane = new JScrollPane(quizListPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void startQuiz(Quiz quiz) {
        QuizPanel quizPanel = new QuizPanel(app, quiz);
        app.addPanel(quizPanel, "Quiz-" + quiz.getName());
        app.showPanel("Quiz-" + quiz.getName());
    }
    
    private void editQuiz(Quiz quiz) {
        QuizEditorPanel editorPanel = new QuizEditorPanel(app, quiz);
        app.addPanel(editorPanel, "QuizEditor-" + quiz.getName());
        app.showPanel("QuizEditor-" + quiz.getName());
    }
    
    private void deleteQuiz(Quiz quiz) {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete the quiz '" + quiz.getName() + "'?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            app.getQuizzes().remove(quiz);
            app.addPanel(new DashboardPanel(app), "Dashboard");
            app.showPanel("Dashboard");
        }
    }
    
    private void showAddQuizDialog() {
        JDialog dialog = new JDialog(app, "Add New Quiz", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(app);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField nameField = new JTextField(20);
        panel.add(new JLabel("Quiz Name:"), BorderLayout.NORTH);
        panel.add(nameField, BorderLayout.CENTER);
        
        JButton createButton = new JButton("Create Quiz");
        createButton.addActionListener(e -> {
            String quizName = nameField.getText().trim();
            if (!quizName.isEmpty()) {
                Quiz newQuiz = new Quiz(quizName);
                app.getQuizzes().add(newQuiz);
                dialog.dispose();
                
                // Open the quiz editor for the new quiz
                QuizEditorPanel editorPanel = new QuizEditorPanel(app, newQuiz);
                app.addPanel(editorPanel, "QuizEditor-" + newQuiz.getName());
                app.showPanel("QuizEditor-" + newQuiz.getName());
            }
        });
        
        panel.add(createButton, BorderLayout.SOUTH);
        dialog.add(panel);
        dialog.setVisible(true);
    }
}

// QuizPanel.java
class QuizPanel extends JPanel {
    private QuizApp app;
    private Quiz quiz;
    private QuizSection currentSection;
    private int currentQuestionIndex;
    private JLabel questionLabel;
    private JPanel optionsPanel;
    private ButtonGroup optionsGroup;
    private JButton nextButton;
    private JButton prevButton;
    
    public QuizPanel(QuizApp app, Quiz quiz) {
        this.app = app;
        this.quiz = quiz;
        this.currentSection = quiz.getSections().get(0);
        this.currentQuestionIndex = 0;
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Navigation Panel (Top)
        JPanel navPanel = new JPanel(new BorderLayout());
        JButton homeButton = new JButton("Home");
        homeButton.addActionListener(e -> app.showPanel("Dashboard"));
        navPanel.add(homeButton, BorderLayout.WEST);
        
        JLabel sectionLabel = new JLabel(currentSection.getName(), SwingConstants.CENTER);
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        navPanel.add(sectionLabel, BorderLayout.CENTER);
        add(navPanel, BorderLayout.NORTH);
        
        // Question Panel (Center)
        JPanel questionPanel = new JPanel(new BorderLayout(10, 10));
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        questionPanel.add(questionLabel, BorderLayout.NORTH);
        
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        questionPanel.add(optionsPanel, BorderLayout.CENTER);
        add(questionPanel, BorderLayout.CENTER);
        
        // Navigation Buttons (Bottom)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        
        prevButton.addActionListener(e -> showPreviousQuestion());
        nextButton.addActionListener(e -> showNextQuestion());
        
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Show first question
        showQuestion();
    }
    
    private void showQuestion() {
        Question question = currentSection.getQuestions().get(currentQuestionIndex);
        questionLabel.setText("<html>" + question.getQuestionText() + "</html>");
        
        optionsPanel.removeAll();
        optionsGroup = new ButtonGroup();
        
        for (int i = 0; i < question.getOptions().length; i++) {
            JRadioButton option = new JRadioButton(question.getOptions()[i]);
            option.setActionCommand(String.valueOf(i));
            if (question.getUserAnswer() == i) {
                option.setSelected(true);
            }
            optionsGroup.add(option);
            optionsPanel.add(option);
        }
        
        prevButton.setEnabled(currentQuestionIndex > 0 || quiz.getSections().indexOf(currentSection) > 0);
        boolean isLastQuestion = currentQuestionIndex == currentSection.getQuestions().size() - 1;
        nextButton.setText(isLastQuestion ? "Finish" : "Next");
        
        revalidate();
        repaint();
    }
    
    private void showPreviousQuestion() {
        saveAnswer();
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
        } else {
            int sectionIndex = quiz.getSections().indexOf(currentSection);
            if (sectionIndex > 0) {
                currentSection = quiz.getSections().get(sectionIndex - 1);
                currentQuestionIndex = currentSection.getQuestions().size() - 1;
            }
        }
        showQuestion();
    }
    
    private void showNextQuestion() {
        saveAnswer();
        if (currentQuestionIndex < currentSection.getQuestions().size() - 1) {
            currentQuestionIndex++;
            showQuestion();
        } else {
            int sectionIndex = quiz.getSections().indexOf(currentSection);
            if (sectionIndex < quiz.getSections().size() - 1) {
                currentSection = quiz.getSections().get(sectionIndex + 1);
                currentQuestionIndex = 0;
                showQuestion();
            } else {
                showResults();
            }
        }
    }
    
    private void saveAnswer() {
        ButtonModel selectedButton = optionsGroup.getSelection();
        if (selectedButton != null) {
            int answer = Integer.parseInt(selectedButton.getActionCommand());
            currentSection.getQuestions().get(currentQuestionIndex).setUserAnswer(answer);
        }
    }
    
    private void showResults() {
        ResultsPanel resultsPanel = new ResultsPanel(app, quiz);
        app.addPanel(resultsPanel, "Results");
        app.showPanel("Results");
    }
}

// ResultsPanel.java
class ResultsPanel extends JPanel {
    public ResultsPanel(QuizApp app, Quiz quiz) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Quiz Results", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        
        int totalScore = 0;
        int totalQuestions = 0;
        
        for (QuizSection section : quiz.getSections()) {
            // Calculate section score
            int correctAnswers = 0;
            for (Question q : section.getQuestions()) {
                if (q.getUserAnswer() == q.getCorrectAnswer()) {
                    correctAnswers++;
                }
                totalQuestions++;
            }
            double percentage = (double) correctAnswers / section.getQuestions().size() * 100;
            section.setScore((int) percentage);
            totalScore += correctAnswers;
            
            // Create section result panel
            JPanel sectionPanel = new JPanel(new BorderLayout(10, 5));
            sectionPanel.setBorder(BorderFactory.createTitledBorder(section.getName()));
            sectionPanel.setMaximumSize(new Dimension(600, 100));
            
            // Create progress bar
            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setValue((int) percentage);
            progressBar.setStringPainted(true);
            progressBar.setString(String.format("%.1f%% (%d/%d)", percentage, 
                correctAnswers, section.getQuestions().size()));
            
            // Set color based on score
            if (percentage >= 70) {
                progressBar.setForeground(new Color(50, 205, 50)); // Green
            } else {
                progressBar.setForeground(new Color(220, 20, 60)); // Red
            }
            
            JLabel scoreLabel = new JLabel(String.format("%d out of %d correct", 
                correctAnswers, section.getQuestions().size()));
            scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            sectionPanel.add(progressBar, BorderLayout.CENTER);
            sectionPanel.add(scoreLabel, BorderLayout.SOUTH);
            
            resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            resultsPanel.add(sectionPanel);
        }
        
        // Add overall score
        double totalPercentage = (double) totalScore / totalQuestions * 100;
        JPanel totalScorePanel = new JPanel(new BorderLayout(10, 5));
        totalScorePanel.setBorder(BorderFactory.createTitledBorder("Overall Score"));
        totalScorePanel.setMaximumSize(new Dimension(600, 100));
        
        JProgressBar totalProgressBar = new JProgressBar(0, 100);
        totalProgressBar.setValue((int) totalPercentage);
        totalProgressBar.setStringPainted(true);
        totalProgressBar.setString(String.format("%.1f%%", totalPercentage));
        
        if (totalPercentage >= 70) {
            totalProgressBar.setForeground(new Color(50, 205, 50));
        } else {
            totalProgressBar.setForeground(new Color(220, 20, 60));
        }
        
        totalScorePanel.add(totalProgressBar, BorderLayout.CENTER);
        resultsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        resultsPanel.add(totalScorePanel);
        
        // Add scroll pane for results
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
        
        // Add buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton reviewButton = new JButton("Review Answers");
        reviewButton.addActionListener(e -> showReviewDialog(app, quiz));
        
        JButton homeButton = new JButton("Return to Dashboard");
        homeButton.addActionListener(e -> app.showPanel("Dashboard"));
        
        JButton retakeButton = new JButton("Retake Quiz");
        retakeButton.addActionListener(e -> retakeQuiz(app, quiz));
        
        buttonPanel.add(reviewButton);
        buttonPanel.add(retakeButton);
        buttonPanel.add(homeButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void showReviewDialog(QuizApp app, Quiz quiz) {
        JDialog reviewDialog = new JDialog(app, "Review Answers", true);
        reviewDialog.setSize(600, 400);
        reviewDialog.setLocationRelativeTo(app);
        
        JPanel reviewPanel = new JPanel();
        reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
        
        for (QuizSection section : quiz.getSections()) {
            JLabel sectionLabel = new JLabel(section.getName());
            sectionLabel.setFont(new Font("Arial", Font.BOLD, 16));
            sectionLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
            sectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            reviewPanel.add(sectionLabel);
            
            for (int i = 0; i < section.getQuestions().size(); i++) {
                Question q = section.getQuestions().get(i);
                JPanel questionPanel = new JPanel();
                questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
                questionPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));
                questionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                
                JLabel questionLabel = new JLabel(String.format("%d. %s", i + 1, q.getQuestionText()));
                questionPanel.add(questionLabel);
                
                String userAnswer = q.getUserAnswer() >= 0 ? q.getOptions()[q.getUserAnswer()] : "Not answered";
                String correctAnswer = q.getOptions()[q.getCorrectAnswer()];
                
                JLabel answerLabel = new JLabel(String.format("Your answer: %s", userAnswer));
                answerLabel.setForeground(q.getUserAnswer() == q.getCorrectAnswer() ? 
                    new Color(50, 205, 50) : new Color(220, 20, 60));
                questionPanel.add(answerLabel);
                
                JLabel correctLabel = new JLabel(String.format("Correct answer: %s", correctAnswer));
                correctLabel.setForeground(new Color(50, 205, 50));
                questionPanel.add(correctLabel);
                
                reviewPanel.add(questionPanel);
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(reviewPanel);
        reviewDialog.add(scrollPane);
        
        reviewDialog.setVisible(true);
    }
    
    private void retakeQuiz(QuizApp app, Quiz quiz) {
        // Reset all answers
        for (QuizSection section : quiz.getSections()) {
            for (Question question : section.getQuestions()) {
                question.setUserAnswer(-1);
            }
        }
        
        // Start quiz again
        QuizPanel quizPanel = new QuizPanel(app, quiz);
        app.addPanel(quizPanel, "Quiz-" + quiz.getName());
        app.showPanel("Quiz-" + quiz.getName());
    }
}

// Add QuizEditorPanel.java for editing quizzes
class QuizEditorPanel extends JPanel {
    private QuizApp app;
    private Quiz quiz;
    
    public QuizEditorPanel(QuizApp app, Quiz quiz) {
        this.app = app;
        this.quiz = quiz;
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Edit Quiz: " + quiz.getName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        JButton renameButton = new JButton("Rename Quiz");
        renameButton.addActionListener(e -> renameQuiz());
        headerPanel.add(renameButton, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Sections Panel
        JPanel sectionsPanel = new JPanel();
        sectionsPanel.setLayout(new BoxLayout(sectionsPanel, BoxLayout.Y_AXIS));
        
        // Add sections
        for (QuizSection section : quiz.getSections()) {
            sectionsPanel.add(createSectionPanel(section));
            sectionsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        
        // Add Section Button
        JButton addSectionButton = new JButton("Add New Section");
        addSectionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addSectionButton.addActionListener(e -> addNewSection());
        
        JPanel addSectionPanel = new JPanel();
        addSectionPanel.setLayout(new BoxLayout(addSectionPanel, BoxLayout.X_AXIS));
        addSectionPanel.add(addSectionButton);
        addSectionPanel.add(Box.createHorizontalGlue());
        sectionsPanel.add(addSectionPanel);
        
        JScrollPane scrollPane = new JScrollPane(sectionsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
        
        // Bottom Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton saveButton = new JButton("Save & Return");
        saveButton.addActionListener(e -> app.showPanel("Dashboard"));
        buttonPanel.add(saveButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void renameQuiz() {
        String newName = JOptionPane.showInputDialog(this, "Enter new quiz name:", quiz.getName());
        if (newName != null && !newName.trim().isEmpty()) {
            quiz.setName(newName.trim());
            app.addPanel(new QuizEditorPanel(app, quiz), "QuizEditor-" + quiz.getName());
            app.showPanel("QuizEditor-" + quiz.getName());
        }
    }
    
    private JPanel createSectionPanel(QuizSection section) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            section.getName()
        ));
        
        // Section Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton renameSectionButton = new JButton("Rename");
        JButton deleteSectionButton = new JButton("Delete Section");
        
        renameSectionButton.addActionListener(e -> renameSection(section));
        deleteSectionButton.addActionListener(e -> deleteSection(section));
        
        headerPanel.add(renameSectionButton);
        headerPanel.add(deleteSectionButton);
        sectionPanel.add(headerPanel);
        
        // Questions List
        JPanel questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        for (int i = 0; i < section.getQuestions().size(); i++) {
            Question question = section.getQuestions().get(i);
            questionsPanel.add(createQuestionPanel(section, question, i + 1));
            questionsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        
        sectionPanel.add(questionsPanel);
        
        // Add Question Button
        JButton addQuestionButton = new JButton("Add New Question");
        addQuestionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addQuestionButton.addActionListener(e -> addNewQuestion(section));
        
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButtonPanel.add(addQuestionButton);
        sectionPanel.add(addButtonPanel);
        
        return sectionPanel;
    }
    
    private JPanel createQuestionPanel(QuizSection section, Question question, int questionNumber) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        JPanel questionHeader = new JPanel(new BorderLayout());
        JLabel numberLabel = new JLabel("Q" + questionNumber + ": ");
        JLabel questionText = new JLabel(question.getQuestionText());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        
        editButton.addActionListener(e -> editQuestion(section, question));
        deleteButton.addActionListener(e -> deleteQuestion(section, question));
        
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        
        questionHeader.add(numberLabel, BorderLayout.WEST);
        questionHeader.add(questionText, BorderLayout.CENTER);
        questionHeader.add(buttonPanel, BorderLayout.EAST);
        
        panel.add(questionHeader, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void renameSection(QuizSection section) {
        String newName = JOptionPane.showInputDialog(this, "Enter new section name:", section.getName());
        if (newName != null && !newName.trim().isEmpty()) {
            section.setName(newName.trim());
            refreshPanel();
        }
    }
    
    private void deleteSection(QuizSection section) {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this section and all its questions?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            quiz.getSections().remove(section);
            refreshPanel();
        }
    }
    
    private void addNewSection() {
        String sectionName = JOptionPane.showInputDialog(this, "Enter section name:");
        if (sectionName != null && !sectionName.trim().isEmpty()) {
            quiz.addSection(new QuizSection(sectionName.trim()));
            refreshPanel();
        }
    }
    
    private void addNewQuestion(QuizSection section) {
        showQuestionDialog(section, null);
    }
    
    private void editQuestion(QuizSection section, Question question) {
        showQuestionDialog(section, question);
    }
    
    private void deleteQuestion(QuizSection section, Question question) {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this question?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            section.getQuestions().remove(question);
            refreshPanel();
        }
    }
    
    private void showQuestionDialog(QuizSection section, Question existingQuestion) {
        JDialog dialog = new JDialog(app, existingQuestion == null ? "Add New Question" : "Edit Question", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(app);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Question Text
        JLabel questionLabel = new JLabel("Question:");
        JTextArea questionField = new JTextArea(3, 40);
        questionField.setLineWrap(true);
        questionField.setWrapStyleWord(true);
        if (existingQuestion != null) {
            questionField.setText(existingQuestion.getQuestionText());
        }
        
        // Options
        JPanel[] optionPanels = new JPanel[4];
        JTextField[] optionFields = new JTextField[4];
        JRadioButton[] correctAnswerButtons = new JRadioButton[4];
        ButtonGroup buttonGroup = new ButtonGroup();
        
        for (int i = 0; i < 4; i++) {
            optionPanels[i] = new JPanel(new BorderLayout(5, 0));
            optionFields[i] = new JTextField(30);
            correctAnswerButtons[i] = new JRadioButton("Correct");
            buttonGroup.add(correctAnswerButtons[i]);
            
            if (existingQuestion != null && i < existingQuestion.getOptions().length) {
                optionFields[i].setText(existingQuestion.getOptions()[i]);
                if (i == existingQuestion.getCorrectAnswer()) {
                    correctAnswerButtons[i].setSelected(true);
                }
            }
            
            optionPanels[i].add(new JLabel("Option " + (i + 1) + ":"), BorderLayout.WEST);
            optionPanels[i].add(optionFields[i], BorderLayout.CENTER);
            optionPanels[i].add(correctAnswerButtons[i], BorderLayout.EAST);
        }
        
        // Add components to panel
        panel.add(questionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JScrollPane(questionField));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        for (JPanel optionPanel : optionPanels) {
            panel.add(optionPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton(existingQuestion == null ? "Add Question" : "Save Changes");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            // Validate inputs
            if (questionField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter a question.");
                return;
            }
            
            // Check if at least two options are filled
            int filledOptions = 0;
            for (JTextField field : optionFields) {
                if (!field.getText().trim().isEmpty()) {
                    filledOptions++;
                }
            }
            if (filledOptions < 2) {
                JOptionPane.showMessageDialog(dialog, "Please enter at least two options.");
                return;
            }
            
            // Check if correct answer is selected
            int selectedAnswer = -1;
            for (int i = 0; i < correctAnswerButtons.length; i++) {
                if (correctAnswerButtons[i].isSelected()) {
                    selectedAnswer = i;
                    break;
                }
            }
            if (selectedAnswer == -1) {
                JOptionPane.showMessageDialog(dialog, "Please select the correct answer.");
                return;
            }
            
            // Create or update question
            String[] options = new String[4];
            for (int i = 0; i < 4; i++) {
                options[i] = optionFields[i].getText().trim();
            }
            
            if (existingQuestion == null) {
                // Create new question
                Question newQuestion = new Question(
                    questionField.getText().trim(),
                    options,
                    selectedAnswer
                );
                section.addQuestion(newQuestion);
            } else {
                // Update existing question
                existingQuestion.setQuestionText(questionField.getText().trim());
                existingQuestion.setOptions(options);
                existingQuestion.setCorrectAnswer(selectedAnswer);
            }
            
            dialog.dispose();
            refreshPanel();
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel);
        
        dialog.add(new JScrollPane(panel));
        dialog.setVisible(true);
    }
    
    private void refreshPanel() {
        app.addPanel(new QuizEditorPanel(app, quiz), "QuizEditor-" + quiz.getName());
        app.showPanel("QuizEditor-" + quiz.getName());
    }
}
