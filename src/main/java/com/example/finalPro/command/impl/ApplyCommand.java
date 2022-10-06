package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.dao.validator.GradesValidator;
import com.example.finalPro.model.entity.Applicant;
import com.example.finalPro.model.entity.Enrollment;
import com.example.finalPro.model.entity.Faculty;
import com.example.finalPro.model.entity.User;
import com.example.finalPro.model.entity.role.UserRole;
import com.example.finalPro.model.entity.state.ApplicantState;
import com.example.finalPro.model.entity.state.EnrollmentState;
import com.example.finalPro.model.service.ApplicantService;
import com.example.finalPro.model.service.FacultyService;
import com.example.finalPro.model.service.exception.ApplicantServiceException;
import com.example.finalPro.model.service.exception.FacultyServiceException;
import com.example.finalPro.util.Messages;
import com.example.finalPro.util.Pages;
import com.example.finalPro.util.Parameters;
import com.example.finalPro.util.matcher.UrlMatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ApplyCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(ApplyCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        boolean isPOST = "POST".equals(request.getMethod());
        String page = isPOST ? Pages.REDIRECT_ERROR_PAGE : Pages.FORWARD_ERROR_PAGE;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        String facultyIdString = request.getParameter(Parameters.ID);
        Enrollment enrollment = (Enrollment) request.getAttribute(Parameters.ENROLLMENT);
        if (enrollment != null && enrollment.getState() == EnrollmentState.OPENED) {
            if (facultyIdString != null && user != null && user.getRole() == UserRole.APPLICANT) {
                int facultyId = Integer.parseInt(facultyIdString);

                try {
                    if (isPOST) {
                        String[] subjectGradesStrings = request.getParameterValues(Parameters.GRADE);
                        if (GradesValidator.validateGrades(subjectGradesStrings)) {
                            Applicant newApplicant = new Applicant();
                            newApplicant.setUserId(user.getUserId());
                            newApplicant.setFacultyId(facultyId);
                            newApplicant.setEnrollmentId(enrollment.getEnrollmentId());
                            newApplicant.setApplicantState(ApplicantState.APPLIED);


                            ApplicantService applicantService = ApplicantService.getInstance();
                            if (applicantService.addApplicant(newApplicant, subjectGradesStrings)) {
                                LOGGER.info("applicant has been added: " + newApplicant);
                                page = Pages.REDIRECT_PERSONAL_SETTINGS;
                            } else {
                                LOGGER.warn("trying to apply while already applied: " + user);
                                session.setAttribute(Parameters.ERROR, Messages.ALREADY_APPLIED);
                                page = UrlMatcher.matchUrl(request.getHeader(Parameters.REFERER));
                            }
                        } else {
                            session.setAttribute(Parameters.ERROR, Messages.INVALID_GRADE);
                            page = UrlMatcher.matchUrl(request.getHeader(Parameters.REFERER));
                        }
                        return new Router(Router.DispatchType.REDIRECT, page);
                    } else {
                        FacultyService facultyService = FacultyService.getInstance();
                        Faculty faculty = facultyService.getById(facultyId);
                        request.setAttribute(Parameters.FACULTY, faculty);
                        LOGGER.info("faculty set as attribute");
                        page = Pages.FORWARD_APPLY_PAGE;
                    }

                } catch (ApplicantServiceException | FacultyServiceException e) {
                    LOGGER.error(e.getMessage());
                    session.setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
                }
            } else {
                LOGGER.warn("wrong input parameters");
                session.setAttribute(Parameters.ERROR, Messages.NO_ACCESS);
            }
        } else {
            LOGGER.warn(Messages.WRONG_ENROLLMENT_STATE);
            session.setAttribute(Parameters.ERROR, Messages.WRONG_ENROLLMENT_STATE);
        }
        return new Router(Router.DispatchType.FORWARD, page);

    }
}