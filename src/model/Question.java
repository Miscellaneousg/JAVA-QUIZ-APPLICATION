package model;

import java.util.List;

public class Question {
    private String prompt;
    private List<String> options;
    private int correctAnswer; // index
    private int userAnswer = -1;

    public Question(String prompt, List<String> options, int correctAnswer) {
        this.prompt = prompt;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getPrompt() { return prompt; }
    public List<String> getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }

    public int getUserAnswer() { return userAnswer; }
    public void setUserAnswer(int a) { userAnswer = a; }
}