package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.entity.Enrollment;
import com.example.finalPro.model.entity.state.EnrollmentState;
import com.example.finalPro.model.service.EnrollmentService;
import com.example.finalPro.model.service.exception.EnrollmentServiceException;

import com.example.finalPro.util.Messages;
import com.example.finalPro.util.Pages;
import com.example.finalPro.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

public class OpenEnrollmentCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(OpenEnrollmentCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {

        String page = Pages.REDIRECT_ERROR_PAGE;
        HttpSession session = request.getSession();

        Enrollment enrollment = (Enrollment) request.getAttribute(Parameters.ENROLLMENT);

        if (enrollment == null || enrollment.getState() == EnrollmentState.CLOSED) {
            EnrollmentService enrollmentService = EnrollmentService.getInstance();
            try {
                enrollmentService.openNewEnrollment(new Timestamp(System.currentTimeMillis()));
                LOGGER.info("new enrollment was opened");
                page = Pages.REDIRECT_MAIN;
            } catch (EnrollmentServiceException e) {
                LOGGER.error(e.getMessage());
                session.setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
            }
        } else {
            LOGGER.debug(Messages.WRONG_ENROLLMENT_STATE);
            session.setAttribute(Parameters.ERROR, Messages.WRONG_ENROLLMENT_STATE);
        }
        return new Router(Router.DispatchType.REDIRECT, page);
    }
}