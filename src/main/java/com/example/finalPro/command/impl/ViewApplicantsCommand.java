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
import java.util.Set;

public class ViewApplicantsCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(ViewApplicantsCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {

        String page = Pages.FORWARD_ERROR_PAGE;
        HttpSession session = request.getSession();
        String enrollmentId = request.getParameter(Parameters.ID);
        User user = (User) session.getAttribute(Parameters.USER);

        ApplicantService applicantService=ApplicantService.getInstance();
        try {
            Set<Applicant> applicants;
            if (enrollmentId == null && user != null && user.getRole() == UserRole.ADMIN) {
                applicants = applicantService.getCurrentApplicants();
            } else if (enrollmentId != null) {
                applicants = applicantService.getApplicantsByEnrollment(Integer.parseInt(enrollmentId));
            } else {
                session.setAttribute(Parameters.ERROR, Messages.WRONG_USER_ROLE);
                LOGGER.debug(Messages.WRONG_USER_ROLE);
                return new Router(Router.DispatchType.FORWARD,page);
            }
            request.setAttribute(Parameters.APPLICANTS, applicants);
            page = Pages.FORWARD_VIEW_APPLICANTS;
            if (!applicants.isEmpty()) {
                LOGGER.info("applicants were found and set as attribute");
            } else {
                LOGGER.warn("applicants weren't found");
            }
        } catch (ApplicantServiceException e) {
            LOGGER.error(e.getMessage());
        }
        return new Router(Router.DispatchType.FORWARD,page);
    }
}