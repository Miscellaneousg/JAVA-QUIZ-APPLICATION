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
