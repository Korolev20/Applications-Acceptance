package com.example.finalPro.model.dao;

import com.example.finalPro.model.dao.exception.UserDaoException;
import com.example.finalPro.model.entity.User;

import java.sql.Connection;

public interface UserDao {


    User getByLogin(String login) throws UserDaoException;
    User getByLogin(Connection connection, String login) throws UserDaoException;

    int insert(Connection connection, User user) throws UserDaoException;
    boolean update(User user) throws UserDaoException;
}
