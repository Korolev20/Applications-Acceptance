package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.entity.Applicant;
import com.example.finalPro.model.service.ApplicantService;
import com.example.finalPro.model.service.exception.ApplicantServiceException;
import com.example.finalPro.model.service.exception.SubjectServiceException;
import com.example.finalPro.util.Messages;
import com.example.finalPro.util.Pages;
import com.example.finalPro.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewApplicationCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(ViewApplicationCommand.class.getName());


    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {

        String page = Pages.FORWARD_ERROR_PAGE;
        int applicantId = Integer.parseInt(request.getParameter(Parameters.ID));


        ApplicantService applicantService=ApplicantService.getInstance();

        try {
            Applicant applicant = applicantService.getApplicantById(applicantId);
            if (applicant != null) {
                request.setAttribute(Parameters.APPLICANT, applicant);
                request.setAttribute(Parameters.SUBJECTS, applicant.getSubjects());
                LOGGER.info("application has been found and set as attribute");

            } else {
                LOGGER.warn("application has not been found");
            }
            page = Pages.FORWARD_VIEW_APPLICATION;
        } catch (ApplicantServiceException | SubjectServiceException e) {
            LOGGER.error(e.getMessage());
            request.getSession().setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
        }
        return new Router(Router.DispatchType.FORWARD,page);
    }
}