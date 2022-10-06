package com.example.finalPro.filter.commandAccessFilter;

import com.example.finalPro.model.entity.role.UserRole;

import javax.servlet.Filter;

public class ApplicantCommandFilter extends CommandAccessFilter implements Filter {

    {
        exclusiveCommands ="applicantCommands";
        userRole= UserRole.APPLICANT;
        logMessage="non-applicant user tried to perform: ";
    }
}