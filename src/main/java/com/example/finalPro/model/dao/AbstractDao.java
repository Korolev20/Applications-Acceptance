package com.example.finalPro.model.dao;

import com.example.finalPro.model.dao.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public  abstract class AbstractDao {
    protected ConnectionPool pool;
    protected Logger logger;

    {
        pool= ConnectionPool.getInstance();
        logger= LogManager.getRootLogger();
    }

}
