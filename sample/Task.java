package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {

    private String title;
    private String description;

    private String priority;
    private LocalDate date;


    public Task(String title, String description, String priority, LocalDate date) {
        this.setTitle(new String(title));

        this.setDescription(new String(description));
        this.setPriority(new String(priority));
        this.setDate(date);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
