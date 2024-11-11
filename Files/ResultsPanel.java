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
