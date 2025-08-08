package model;

import java.util.ArrayList;
import java.util.List;

public class QuizSection {
    private String title;
    private List<Question> questions = new ArrayList<>();

    public QuizSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public int correctAnswers() {
        int c = 0;
        for (Question q : questions) {
            if (q.getUserAnswer() == q.getCorrectAnswer()) c++;
        }
        return c;
    }

    public void resetAnswers() {
        questions.forEach(q -> q.setUserAnswer(-1));
    }
}