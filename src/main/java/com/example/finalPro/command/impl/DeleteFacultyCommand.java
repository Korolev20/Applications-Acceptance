package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.service.FacultyService;
import com.example.finalPro.model.service.exception.FacultyServiceException;
import com.example.finalPro.util.Messages;
import com.example.finalPro.util.Pages;
import com.example.finalPro.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteFacultyCommand implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(DeleteFacultyCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {

        String page = Pages.REDIRECT_ERROR_PAGE;
        int facultyId = Integer.parseInt(request.getParameter(Parameters.ID));
        String facultyName = request.getParameter(Parameters.FACULTY_NAME);


        FacultyService facultyService = FacultyService.getInstance();
        try {
            facultyService.deleteFaculty(facultyId);
            LOGGER.info("faculty has been deleted: " + facultyName);
            page = Pages.REDIRECT_VIEW_FACULTIES;
        } catch (FacultyServiceException e) {
            LOGGER.error(e.getMessage());
            request.getSession().setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
        }
        return new Router(Router.DispatchType.REDIRECT, page);
    }
}
