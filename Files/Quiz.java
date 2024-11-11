// Quiz.java
// Update Quiz.java to add new methods
class Quiz {
    private String name;
    private ArrayList<QuizSection> sections;
    
    public Quiz(String name) {
        this.name = name;
        this.sections = new ArrayList<>();
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void addSection(QuizSection section) {
        sections.add(section);
    }
    
    public String getName() {
        return name;
    }
    
    public ArrayList<QuizSection> getSections() {
        return sections;
    }
}

