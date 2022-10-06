package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.entity.Enrollment;
import com.example.finalPro.model.service.EnrollmentService;
import com.example.finalPro.model.service.exception.EnrollmentServiceException;
import com.example.finalPro.util.Messages;
import com.example.finalPro.util.Pages;
import com.example.finalPro.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewEnrollmentsCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(ViewEnrollmentsCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        EnrollmentService enrollmentService = EnrollmentService.getInstance();
        try {
            List<Enrollment> enrollments = enrollmentService.getAllClosedEnrollments();
            if (!enrollments.isEmpty()) {
                LOGGER.info("enrollments were found and set as attribute");
                request.setAttribute(Parameters.ENROLLMENTS, enrollments);
            } else {
                LOGGER.warn("enrollments weren't found");
            }
        } catch (EnrollmentServiceException e) {
            LOGGER.error(e.getMessage());
            request.getSession().setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
        }
        return new Router(Router.DispatchType.FORWARD,Pages.FORWARD_VIEW_ENROLLMENTS) ;
    }
}