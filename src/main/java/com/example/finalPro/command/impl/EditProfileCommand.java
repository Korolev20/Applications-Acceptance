package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.dao.validator.UserDataValidator;
import com.example.finalPro.model.entity.User;
import com.example.finalPro.model.service.UserService;
import com.example.finalPro.model.service.exception.UserServiceException;
import com.example.finalPro.util.Messages;
import com.example.finalPro.util.Pages;
import com.example.finalPro.util.Parameters;
import com.example.finalPro.util.matcher.UrlMatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class EditProfileCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(EditProfileCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String page = UrlMatcher.matchUrl(request.getHeader(Parameters.REFERER));

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        boolean isGet = "GET".equals(request.getMethod());
        if (isGet) {
            page = Pages.FORWARD_EDIT_PROFILE;
            return new Router(Router.DispatchType.FORWARD, page);
        } else {
            String password = request.getParameter(Parameters.PASSWORD);
            if (password != null && password.hashCode() == user.getPassword()) {

                String firstName = request.getParameter(Parameters.FIRST_NAME);
                String lastName = request.getParameter(Parameters.LAST_NAME);
                String patronymic = request.getParameter(Parameters.PATRONYMIC);
                List<String> errorMessages = UserDataValidator.validateEditingForm(lastName, firstName, patronymic);
                if (errorMessages == null) {
                    user.setLastName(lastName);
                    user.setFirstName(firstName);
                    user.setPatronymic(patronymic);

                    UserService userService = UserService.getInstance();
                    try {
                        userService.editUser(user);
                        LOGGER.info("user has been edit: " + user);
                        session.setAttribute(Parameters.USER, user);
                        page = Pages.REDIRECT_PERSONAL_SETTINGS;
                    } catch (UserServiceException e) {
                        System.out.println("YESSSSSSSSSS");
                        LOGGER.error(e.getMessage());
                        request.getSession().setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
                        page = Pages.REDIRECT_ERROR_PAGE;
                    }
                } else {
                    session.setAttribute(Parameters.ERRORS, errorMessages);
                }
            } else {
                session.setAttribute(Parameters.ERROR, Messages.INCORRECT_PASSWORD);
            }
        }
        return new Router(Router.DispatchType.REDIRECT, page);
    }
}