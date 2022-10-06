package com.example.finalPro.model.entity;

import com.example.finalPro.model.entity.role.UserRole;

import java.util.Objects;

public class User {

        private int userId;
        private UserRole role;
        private String login;
        private int password;
        private String firstName;
        private String lastName;
        private String patronymic;

        public User() {

        }

        public User(UserRole role, String login, int password, String firstName, String lastName, String patronymic) {
            this.role = role;
            this.login = login;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.patronymic = patronymic;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public UserRole getRole() {
            return role;
        }

        public void setRole(UserRole role) {
            this.role = role;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public int getPassword() {
            return password;
        }

        public void setPassword(int password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && password == user.password && role == user.role && Objects.equals(login, user.login) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(patronymic, user.patronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, role, login, password, firstName, lastName, patronymic);
    }

    @Override
        public String toString() {
            return "User{" +
                    "userId=" + userId +
                    ", role=" + role +
                    ", login='" + login + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", patronymic='" + patronymic + '\'' +
                    '}';
        }
    }

