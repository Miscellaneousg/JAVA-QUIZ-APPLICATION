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
