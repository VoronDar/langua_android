package com.example.langua.Databases.FirebaseAccess.pojo;

public final class Course {
    private int version = 0;
    private boolean accessibility = true;
    private boolean hasAccess = true;
    private String id;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isAccessibility() {
        return accessibility;
    }

    public void setAccessibility(boolean accessibility) {
        this.accessibility = accessibility;
    }

    public boolean isHasAccess() {
        return hasAccess;
    }

    public void setHasAccess(boolean hasAccess) {
        this.hasAccess = hasAccess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // конструктор для sql - содержит последнюю загруженную версию
    public Course(String id, int version, boolean hasAccess) {
        this.version = version;
        this.hasAccess = hasAccess;
        this.id = id;
    }
}
