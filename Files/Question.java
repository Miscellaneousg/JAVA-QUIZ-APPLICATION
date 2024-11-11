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
