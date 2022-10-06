package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.entity.User;
import com.example.finalPro.util.Pages;
import com.example.finalPro.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(LogOutCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);

        if (user != null) {
            session.invalidate();
            LOGGER.info("user: " + user.getLogin()+" was log out");
        } else {
            LOGGER.debug("could not log out: user not identified");
        }
        return new Router(Router.DispatchType.REDIRECT,Pages.FORWARD_MAIN);

    }
}