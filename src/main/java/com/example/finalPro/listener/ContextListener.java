package com.example.finalPro.listener;

import com.example.finalPro.model.dao.connection.ConnectionPool;
import com.example.finalPro.model.dao.exception.DaoException;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        connectionPool.initPool();

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        connectionPool.closeAll();

    }
}
