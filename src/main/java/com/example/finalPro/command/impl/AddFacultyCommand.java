package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.dao.validator.FacultyDataValidator;
import com.example.finalPro.model.entity.Faculty;
import com.example.finalPro.model.entity.Subject;
import com.example.finalPro.model.service.FacultyService;
import com.example.finalPro.model.service.SubjectService;
import com.example.finalPro.model.service.exception.FacultyServiceException;
import com.example.finalPro.model.service.exception.SubjectServiceException;
import com.example.finalPro.util.Messages;
import com.example.finalPro.util.Pages;
import com.example.finalPro.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class AddFacultyCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(AddFacultyCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        boolean isGET = "GET".equals(request.getMethod());
        String page = isGET ? Pages.FORWARD_ERROR_PAGE : Pages.REDIRECT_ERROR_PAGE;
        HttpSession session = request.getSession();
        try {
            if (isGET) {
                SubjectService subjectService = SubjectService.getInstance();
                List<Subject> subjects = subjectService.getAllSubjects();
                request.setAttribute(Parameters.SUBJECTS, subjects);
                LOGGER.info("subjects has been gotten and set as attribute");
                page = Pages.FORWARD_ADD_FACULTY;
                return new Router(Router.DispatchType.FORWARD, page);
            } else {
                String facultyName = request.getParameter(Parameters.FACULTY_NAME);
                String[] subjectsIdStrings = request.getParameterValues(Parameters.SUBJECT_ID);
                String capacityString = request.getParameter(Parameters.CAPACITY);
                if (FacultyDataValidator.validateFacultyName(facultyName) && subjectsIdStrings != null && FacultyDataValidator.validateCapacity(capacityString)) {
                    List<Subject> requiredSubjects = new ArrayList<>();
                    Subject subject;
                    for (String idString : subjectsIdStrings) {
                        subject = new Subject();
                        subject.setSubjectId(Integer.parseInt(idString));
                        requiredSubjects.add(subject);
                    }
                    Faculty faculty = new Faculty();
                    faculty.setName(facultyName);
                    faculty.setCapacity(Integer.parseInt(capacityString));
                    faculty.setRequiredSubjects(requiredSubjects);

                    FacultyService facultyService = FacultyService.getInstance();

                    facultyService.addFaculty(faculty);

                    LOGGER.info("faculty has been added: " + faculty);
                    page = Pages.REDIRECT_VIEW_FACULTIES;
                } else {
                    page = Pages.REDIRECT_ADD_FACULTY;
                    session.setAttribute(Parameters.ERROR, Messages.INVALID_FACULTY_DATA);
                    LOGGER.warn(Messages.INVALID_FACULTY_DATA);
                }
            }
        } catch (FacultyServiceException | SubjectServiceException | NumberFormatException e) {
            LOGGER.error(e.getMessage());
            session.setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
        }
        return new Router(Router.DispatchType.REDIRECT, page);
    }

}