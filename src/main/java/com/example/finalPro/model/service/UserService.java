package com.example.finalPro.model.service;


import com.example.finalPro.model.dao.UserDao;
import com.example.finalPro.model.dao.exception.UserDaoException;
import com.example.finalPro.model.entity.User;
import com.example.finalPro.model.service.exception.UserServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;


public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
    private static final UserService instance = new UserService();

    public UserService() {
    }

    public static UserService getInstance() {
        return instance;
    }

    public User login(String login) throws UserServiceException {
        try {
            UserDao userDao = DaoHolder.getUserDao();
            return userDao.getByLogin(login);
        } catch (UserDaoException e) {
            logger.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }


    }

    public int addUser(Connection connection, User user) throws UserServiceException {

        try {
            UserDao userDao = DaoHolder.getUserDao();
            return userDao.insert(connection,user);
        } catch (UserDaoException e) {
            logger.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public boolean editUser(User user) throws UserServiceException {
        try {
            UserDao userDao = DaoHolder.getUserDao();
            return userDao.update(user);
        } catch (UserDaoException e) {
            logger.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public boolean checkLoginUniqueness(Connection connection,String login) throws UserServiceException {

        try {
            UserDao userDao = DaoHolder.getUserDao();
            User user=userDao.getByLogin(connection,login);
            return user == null;

        } catch (UserDaoException e) {
            logger.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }
}