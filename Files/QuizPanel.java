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
