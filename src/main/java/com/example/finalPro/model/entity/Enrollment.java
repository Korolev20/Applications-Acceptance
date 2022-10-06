package com.example.finalPro.model.entity;

import com.example.finalPro.model.entity.state.EnrollmentState;

import java.sql.Timestamp;
import java.util.Objects;

public class Enrollment {

    private int enrollmentId;
    private EnrollmentState state;
    private Timestamp startDate;
    private Timestamp endDate;

    public Enrollment() {

    }

    public Enrollment(int enrollmentId, EnrollmentState state, Timestamp startDate, Timestamp endDate) {
        this.enrollmentId = enrollmentId;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public EnrollmentState getState() {
        return state;
    }

    public void setState(EnrollmentState state) {
        this.state = state;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return enrollmentId == that.enrollmentId && state == that.state && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId, state, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", state=" + state +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}