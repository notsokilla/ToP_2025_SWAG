package Misc;

public class Quest {
    private String title;
    private String description;
    private static boolean completed;

    public Quest(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return title + ": " + description + " [" + (completed ? "Выполнено" : "Не выполнено") + "]";
    }
}