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

