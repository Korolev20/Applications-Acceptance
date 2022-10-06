package com.example.finalPro.model.entity;

import java.util.Objects;

public class Subject {

    private int subjectId;
    private String name;
    private int grade;

    public Subject() {

    }

    public Subject(int subjectId, String name, int grade) {
        this.subjectId = subjectId;
        this.name = name;
        this.grade = grade;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return subjectId == subject.subjectId && grade == subject.grade && Objects.equals(name, subject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId, name, grade);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                '}';
    }

}
