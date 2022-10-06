package com.example.finalPro.model.dao.exception;

import java.sql.SQLException;

public class DaoException extends Exception {

    private static final long serialVersionUID= -4854972581204568010L;

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, SQLException e) {
    }
}