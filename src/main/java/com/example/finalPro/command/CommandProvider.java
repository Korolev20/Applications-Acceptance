package com.example.finalPro.command;

import com.example.finalPro.command.impl.*;

import java.util.EnumMap;
import java.util.Optional;

import static com.example.finalPro.command.CommandType.*;

public class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();
    private final EnumMap<CommandType, ICommand> commands = new EnumMap<>(CommandType.class);

    private CommandProvider() {
        commands.put(LOGIN, new LoginCommand());
        commands.put(REGISTRATION, new RegistrationCommand());
        commands.put(PERSONAL_SETTINGS, new PersonalSettingsCommand());
        commands.put(VIEW_FACULTIES, new ViewFacultiesCommand());
        commands.put(VIEW_SUBJECTS, new ViewSubjectsCommand());
        commands.put(VIEW_FACULTY, new ViewFacultyCommand());
        commands.put(LOGOUT, new LogOutCommand());
        commands.put(VIEW_ENROLLMENTS, new ViewEnrollmentsCommand());
        commands.put(VIEW_APPLICATION, new ViewApplicationCommand());
        commands.put(VIEW_APPLICANTS, new ViewApplicantsCommand());
        commands.put(OPEN_ENROLLMENT, new OpenEnrollmentCommand());
        commands.put(CLOSE_ENROLLMENT, new CloseEnrollmentCommand());
        commands.put(EDIT_PROFILE, new EditProfileCommand());
        commands.put(DELETE_SUBJECT, new DeleteSubjectCommand());
        commands.put(ADD_SUBJECT, new AddSubjectCommand());
        commands.put(CANCEL_APPLICATION, new CancelApplication());
        commands.put(ADD_FACULTY, new AddFacultyCommand());
        commands.put(APPLY,new ApplyCommand());
        commands.put(DELETE_FACULTY,new DeleteFacultyCommand());
    }


    public static CommandProvider getInstance() {
        return instance;
    }


    public Optional<ICommand> defineCommand(String commandName) {
        Optional<CommandType> commandType = typeForName(commandName);
        if (commandType.isPresent()) {
            ICommand command = commands.get(commandType.get());
            return Optional.of(command);
        }
        return Optional.empty();
    }

    public Optional<CommandType> typeForName(String commandName) {
        try {
            CommandType commandType = valueOf(commandName.toUpperCase());
            return Optional.of(commandType);
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}