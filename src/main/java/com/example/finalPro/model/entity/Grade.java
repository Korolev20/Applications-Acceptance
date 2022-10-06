package com.example.finalPro.model.entity;

import java.util.Objects;

public class Grade {
    private String grade;
    private int subjectId;
    private int applicantId;

    public Grade(){

    }

    public Grade(String grade, int subjectId, int applicantId) {
        this.grade = grade;
        this.subjectId = subjectId;
        this.applicantId = applicantId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return subjectId == grade1.subjectId && applicantId == grade1.applicantId && Objects.equals(grade, grade1.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grade, subjectId, applicantId);
    }

    @Override
    public String toString() {
        return "Grade{" +
                "grade='" + grade + '\'' +
                '}';
    }
}
