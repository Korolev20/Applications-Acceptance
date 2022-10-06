
package com.example.finalPro.command.impl;
import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.dao.connection.ConnectionPool;
import com.example.finalPro.model.dao.validator.UserDataValidator;
import com.example.finalPro.model.entity.User;
import com.example.finalPro.model.entity.role.UserRole;
import com.example.finalPro.model.service.UserService;
import com.example.finalPro.model.service.exception.UserServiceException;

import com.example.finalPro.util.Messages;
import com.example.finalPro.util.Pages;
import com.example.finalPro.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;


public class RegistrationCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommand.class.getName());


    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        boolean isPost = "POST".equals(request.getMethod());
        String page = Pages.REDIRECT_REGISTRATION;
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            if (isPost) {
                HttpSession session = request.getSession();
                String login = request.getParameter(Parameters.LOGIN);
                String password = request.getParameter(Parameters.PASSWORD);
                String repeatPassword = request.getParameter(Parameters.REPEAT_PASSWORD);
                String firstName = request.getParameter(Parameters.FIRST_NAME);
                String lastName = request.getParameter(Parameters.LAST_NAME);
                String patronymic = request.getParameter(Parameters.PATRONYMIC);
                String userRole = request.getParameter(Parameters.USER_ROLE);

                List<String> errorMessages = UserDataValidator.validateRegistrationForm(login, password, repeatPassword, lastName, firstName, patronymic, userRole);
                UserService userService1 = UserService.getInstance();
                boolean uniquenessCheck = userService1.checkLoginUniqueness(connection,login);
                if (errorMessages == null && uniquenessCheck) {

                    User newUser = new User(UserRole.valueOf(userRole.toUpperCase()), login, password.hashCode(), firstName, lastName, patronymic);
                    newUser.setUserId(userService1.addUser(connection,newUser));

                    if (session.getAttribute(Parameters.USER) != null) {
                        session.invalidate();
                    }
                    session.setAttribute(Parameters.USER, newUser);
                    LOGGER.info("new user has been registered and log in as: " + newUser);
                    page = Pages.REDIRECT_PERSONAL_SETTINGS;
                    return new Router(Router.DispatchType.REDIRECT, page);
                } else {
                    String[] inputValues = {login, null, null, lastName, firstName, patronymic, userRole};
                    List<String> validValues = selectValidValues(inputValues, errorMessages);
                    LOGGER.debug(errorMessages);
                    session.setAttribute(Parameters.VALID_VALUES, validValues);
                    session.setAttribute(Parameters.ERRORS, errorMessages);
                    assert errorMessages != null;
                    if (errorMessages.get(0) == null && !uniquenessCheck) {
                        LOGGER.debug(Messages.LOGIN_NOT_UNIQUE);
                        session.setAttribute(Parameters.ERROR, Messages.LOGIN_NOT_UNIQUE);
                    }
                }
            } else {
                page = Pages.FORWARD_REGISTRATION;
                return new Router(Router.DispatchType.FORWARD,page);
            }
        } catch (UserServiceException e) {
            LOGGER.error(e.getMessage());
            request.getSession().setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
            page = Pages.REDIRECT_ERROR_PAGE;
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
        return new Router(Router.DispatchType.REDIRECT,page);
    }

    private List<String> selectValidValues(String[] inputValues, List<String> errorMessages) {

        for (int i = 0; i < inputValues.length; i++) {
            if (inputValues[i] != null) {
                if (errorMessages.get(i) != null) {
                    inputValues[i] = null;
                }
            }
        }
        return Arrays.asList(inputValues);
    }
}