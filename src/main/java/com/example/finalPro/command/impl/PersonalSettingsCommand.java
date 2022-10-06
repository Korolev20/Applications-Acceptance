package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.entity.Applicant;
import com.example.finalPro.model.entity.User;
import com.example.finalPro.model.entity.role.UserRole;
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
import java.util.Collections;
import java.util.List;


public class PersonalSettingsCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(PersonalSettingsCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String page = Pages.FORWARD_ERROR_PAGE;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);

        if (user != null) {
            page = Pages.FORWARD_PERSONAL_SETTINGS;
            if (user.getRole() == UserRole.APPLICANT) {
                ApplicantService applicantService = ApplicantService.getInstance();
                try {
                    List<Applicant> applicants = applicantService.getApplicantsByUserId(user.getUserId());
                    if (!applicants.isEmpty()) {
                        Collections.reverse(applicants);
                        request.setAttribute(Parameters.APPLICANTS, applicants);
                        LOGGER.info("applications were found and set as attribute");
                    }
                } catch (ApplicantServiceException e) {
                    LOGGER.error(e.getMessage());
                    session.setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
                }
            }
        } else {
            session.setAttribute(Parameters.ERROR, Messages.NO_ACCESS);
        }
        return new Router(Router.DispatchType.FORWARD, page);
    }
}