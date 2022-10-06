package com.example.finalPro.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ICommand {

    Router execute(HttpServletRequest request, HttpServletResponse response);

}
