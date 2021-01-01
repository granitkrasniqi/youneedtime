package dev.granitkrasniqi.yntcommoncode.domain;

public class ToDo {
    private String id;
    private String text;
    private Long createdAt;
    private ToDoStatus status;
    private double expectedHours;

    public ToDo() {
    }

    public ToDo(String id, String text, Long createdAt, ToDoStatus status, double expectedHours) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.status = status;
        this.expectedHours = expectedHours;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public ToDoStatus getStatus() {
        return status;
    }

    public void setStatus(ToDoStatus status) {
        this.status = status;
    }

    public double getExpectedHours() {
        return expectedHours;
    }

    public void setExpectedHours(double expectedHours) {
        this.expectedHours = expectedHours;
    }
}
