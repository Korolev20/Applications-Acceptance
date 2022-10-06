package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.entity.User;
import com.example.finalPro.model.service.ApplicantService;
import com.example.finalPro.model.service.exception.ApplicantServiceException;
import com.example.finalPro.util.Messages;
import com.example.finalPro.util.Pages;
import com.example.finalPro.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CancelApplication implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(CancelApplication.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {

        String page = Pages.REDIRECT_ERROR_PAGE;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        String applicantIdString = request.getParameter(Parameters.APPLICANT_ID);
        int applicantId = Integer.parseInt(applicantIdString);
        ApplicantService applicantService = ApplicantService.getInstance();
        try {
            if (applicantService.cancelApplication(applicantId, user)) {
                LOGGER.info("application has been deleted by user: " + user);
                page = Pages.REDIRECT_PERSONAL_SETTINGS;

            } else {
                LOGGER.warn("user: " + user + "trying to delete not his application");
                page = Pages.REDIRECT_ERROR_PAGE;
                session.setAttribute(Parameters.ERROR, Messages.NO_ACCESS);
            }
        } catch (ApplicantServiceException e) {
            LOGGER.error(e.getMessage());
            session.setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
        }
        return new Router(Router.DispatchType.REDIRECT, page);
    }
}