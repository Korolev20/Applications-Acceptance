package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
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
import java.util.List;

public class ViewSubjectsCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(ViewSubjectsCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {

        String page = Pages.FORWARD_ERROR_PAGE;

        SubjectService subjectService = SubjectService.getInstance();
        try {
            List<Subject> subjects = subjectService.getAllSubjects();
            if (subjects != null) {
                LOGGER.info("subjects were found and set as attribute");
                request.setAttribute(Parameters.SUBJECTS, subjects);
            } else {
                LOGGER.warn("subjects weren't found");
            }
            page = Pages.FORWARD_VIEW_SUBJECTS;

        } catch (SubjectServiceException e) {
            LOGGER.error(e.getMessage());
            request.getSession().setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
        }
        return new Router(Router.DispatchType.FORWARD, page);
    }
}