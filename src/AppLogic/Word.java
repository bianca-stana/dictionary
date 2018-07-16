package AppLogic;

public class Word {
    private int ID;
    private String word;
    private String explanation;
    private String category;
    private int views;

    public Word(int ID, String word, String explanation, String category, int views) {
        this.ID = ID;
        this.word = word;
        this.explanation = explanation;
        this.category = category;
        this.views = views;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getExplanation() {
        return explanation;
    }
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public int getViews() {
        return views;
    }
    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "Word{" +
                "ID=" + ID +
                ", word='" + word + '\'' +
                ", explanation='" + explanation + '\'' +
                ", category='" + category + '\'' +
                ", views=" + views +
                '}';
    }
}
