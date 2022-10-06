package com.example.finalPro.model.entity;

import java.util.List;
import java.util.Objects;

public class Faculty {
    private int id;
    private String name;
    private int capacity;
    private List<Subject> requiredSubjects;

    public Faculty() {

    }

    public Faculty(int id, String name, int capacity, List<Subject> requiredSubjects) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.requiredSubjects = requiredSubjects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Subject> getRequiredSubjects() {
        return requiredSubjects;
    }

    public void setRequiredSubjects(List<Subject> requiredSubjects) {
        this.requiredSubjects = requiredSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id && capacity == faculty.capacity && Objects.equals(name, faculty.name) && Objects.equals(requiredSubjects, faculty.requiredSubjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, requiredSubjects);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                ", requiredSubjects=" + requiredSubjects +
                '}';
    }
}

