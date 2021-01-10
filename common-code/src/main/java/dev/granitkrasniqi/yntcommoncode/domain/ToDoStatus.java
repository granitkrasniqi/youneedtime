package dev.granitkrasniqi.yntcommoncode.domain;

public enum ToDoStatus {
    TO_DO("ToDo"),
    IN_PROGRESS("InProgress"),
    DONE("Done");

    private final String value;

    ToDoStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
