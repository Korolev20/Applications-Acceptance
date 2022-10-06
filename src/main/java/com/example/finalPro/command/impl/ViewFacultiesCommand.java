package com.example.finalPro.command.impl;

import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.model.entity.Faculty;
import com.example.finalPro.model.service.FacultyService;
import com.example.finalPro.model.service.exception.FacultyServiceException;

import com.example.finalPro.util.Messages;
import com.example.finalPro.util.Pages;
import com.example.finalPro.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewFacultiesCommand implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(ViewFacultiesCommand.class.getName());

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {

        String page = Pages.FORWARD_ERROR_PAGE;
        FacultyService facultyService=FacultyService.getInstance();
        try {
            List<Faculty> faculties = facultyService.getAllFaculties();
            if (!faculties.isEmpty()) {
                LOGGER.info("faculties were found and set as attribute");
                request.setAttribute(Parameters.FACULTIES, faculties);
            } else {
                LOGGER.warn("faculties weren't found");
            }
            page = Pages.FORWARD_VIEW_FACULTIES;
        } catch (FacultyServiceException e) {
            LOGGER.error(e.getMessage());
            request.getSession().setAttribute(Parameters.ERROR, Messages.INTERNAL_ERROR);
        }
        return new Router(Router.DispatchType.FORWARD,page);
    }
}