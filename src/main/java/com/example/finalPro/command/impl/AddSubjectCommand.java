package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.dao.validator.FacultyDataValidator;
import com.example.finalPro.model.entity.Subject;
import com.example.finalPro.model.service.SubjectService;
import com.example.finalPro.model.service.exception.SubjectServiceException;

import com.example.finalPro.util.Messages;
import com.example.finalPro.util.Pages;
import com.example.finalPro.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddSubjectCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(AddSubjectCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {

        String page = Pages.REDIRECT_VIEW_SUBJECTS;
        String subjectName = request.getParameter(Parameters.SUBJECT_NAME);

        SubjectService subjectService = SubjectService.getInstance();
        if (FacultyDataValidator.validateSubjectName(subjectName)) {
            Subject subject = new Subject();
            subject.setName(subjectName);
            try {
                subjectService.insert(subject);
                LOGGER.info("subject was added: " + subject);
            } catch (SubjectServiceException e) {
                LOGGER.error(e.getMessage());
                page = Pages.REDIRECT_ERROR_PAGE;
                request.getSession().setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
            }
        } else {
            LOGGER.warn("invalid faculty data name");
            request.getSession().setAttribute(Parameters.ERROR, Messages.INVALID_SUBJECT_NAME);
        }
        return new Router(Router.DispatchType.REDIRECT, page);
    }
}