package com.example.finalPro.controller;
import com.example.finalPro.command.CommandProvider;
import com.example.finalPro.command.ICommand;
import com.example.finalPro.command.Router;
import com.example.finalPro.util.Messages;
import com.example.finalPro.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.example.finalPro.command.Router.DispatchType.FORWARD;
import static com.example.finalPro.util.Parameters.COMMAND;

public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp );
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        CommandProvider commandProvider = CommandProvider.getInstance();
        Optional<ICommand> commandOptional = commandProvider.defineCommand(commandName);
        Router router;
        if (commandOptional.isPresent()) {
            ICommand command = commandOptional.get();
            router = command.execute(request, response);
        } else {
            request.setAttribute(Parameters.ERROR, "Command is not presented");
            router = new Router(FORWARD, Messages.ERROR);
        }
        switch (router.getDispatchType()) {
            case FORWARD:
                RequestDispatcher dispatcher = request.getRequestDispatcher(router.getTargetPath());
                dispatcher.forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(request.getContextPath() +"/" + router.getTargetPath());
                break;
            default:
                logger.error("Invalid router type!");
                response.sendError(500);
        }
    }
}

