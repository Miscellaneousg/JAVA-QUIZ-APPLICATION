package model;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private String name;
    private List<QuizSection> sections = new ArrayList<>();

    public Quiz(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<QuizSection> getSections() {
        return sections;
    }

    public void addSection(QuizSection s) {
        sections.add(s);
    }

    public void addSectionFromTitle(String title) {
        sections.add(new QuizSection(title));
    }

    public int totalQuestions() {
        return sections.stream().mapToInt(s -> s.getQuestions().size()).sum();
    }

    public int totalCorrect() {
        return sections.stream().mapToInt(QuizSection::correctAnswers).sum();
    }

    public int scorePercent() {
        int total = totalQuestions();
        if (total == 0) return 0;
        return (int) Math.round((totalCorrect() * 100.0) / total);
    }

    public void resetUserAnswers() {
        sections.forEach(QuizSection::resetAnswers);
    }
}