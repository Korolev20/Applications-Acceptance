package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.service.SubjectService;
import com.example.finalPro.model.service.exception.SubjectServiceException;
import com.example.finalPro.util.Messages;
import com.example.finalPro.util.Pages;
import com.example.finalPro.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteSubjectCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(DeleteSubjectCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {

        String page = Pages.REDIRECT_VIEW_SUBJECTS;
        String subjectIdString = request.getParameter(Parameters.SUBJECT_ID);
        String subjectName = request.getParameter(Parameters.SUBJECT_NAME);


        SubjectService subjectService = SubjectService.getInstance();
        try {
            int subjectId = Integer.parseInt(subjectIdString);
            subjectService.deleteSubject(subjectId);
            LOGGER.info("subject has been deleted: " + subjectName);
        } catch (SubjectServiceException | NumberFormatException e) {
            LOGGER.error(e.getMessage());
            page = Pages.REDIRECT_ERROR_PAGE;
            request.getSession().setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
        }
        return new Router(Router.DispatchType.REDIRECT, page);
    }
}