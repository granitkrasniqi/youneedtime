package dev.granitkrasniqi.yntcommoncode.domain;

public class ToDo {
    public final static String ID_FIELD = "id";
    public final static String TEXT_FIELD = "text";
    public final static String CREATED_AT_FIELD = "createdAt";
    public final static String STATUS_FIELD = "status";
    public final static String EXPECTED_DONE_HOURS_FIELD = "expectedDoneHours";

    private String id;
    private String text;
    private Long createdAt;
    private ToDoStatus status;
    private double expectedDoneHours;

    public ToDo() {
    }

    public ToDo(String id, String text, Long createdAt, ToDoStatus status, double expectedDoneHours) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.status = status;
        this.expectedDoneHours = expectedDoneHours;
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

    public double getExpectedDoneHours() {
        return expectedDoneHours;
    }

    public void setExpectedDoneHours(double expectedDoneHours) {
        this.expectedDoneHours = expectedDoneHours;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", status=" + status.getValue() +
                ", expectedDoneHours=" + expectedDoneHours +
                '}';
    }
}
