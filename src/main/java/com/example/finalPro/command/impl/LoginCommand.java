package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.entity.User;
import com.example.finalPro.model.service.UserService;
import com.example.finalPro.model.service.exception.UserServiceException;
import com.example.finalPro.util.Messages;

import com.example.finalPro.util.Parameters;
import com.example.finalPro.util.matcher.UrlMatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {

        String page = UrlMatcher.matchUrl(request.getHeader(Parameters.REFERER));
        HttpSession session = request.getSession();
        String login = request.getParameter(Parameters.LOGIN);
        String password = request.getParameter(Parameters.PASSWORD);

        LOGGER.debug("login: " + login + " password hashCode: " + password.hashCode());

        if (!password.equals("") && !login.equals("")) {
            UserService userService = UserService.getInstance();
            User user;
            try {
                user = userService.login(login);

                if (user != null) {
                    LOGGER.debug("found user: " + user);
                    long passwordHash = user.getPassword();

                    if (passwordHash == password.hashCode()) {
                        LOGGER.info(passwordHash);
                        LOGGER.debug("successful login as " + user.getRole());
                        session.setAttribute(Parameters.USER, user);

                    } else {
                        LOGGER.info("incorrect password");
                        session.setAttribute(Parameters.LOGIN_ERROR, Messages.INCORRECT_PASSWORD);
                    }
                } else {
                    LOGGER.info("incorrect login");
                    session.setAttribute(Parameters.LOGIN_ERROR, Messages.INCORRECT_LOGIN);
                }
            } catch (UserServiceException e) {
                LOGGER.error(e.getMessage());
                session.setAttribute(Parameters.LOGIN_ERROR, Messages.INTERNAL_ERROR);
            }
        } else {
            LOGGER.info("field not filled");
            session.setAttribute(Parameters.LOGIN_ERROR, Messages.FIELDS_NOT_FILLED);
        }
        return new Router(Router.DispatchType.REDIRECT, page);
    }
}