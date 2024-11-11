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

